package com.vocadabulary.services;

import com.vocadabulary.auth.MockUser;
import com.vocadabulary.auth.MockUserContext;
import com.vocadabulary.dto.SentenceDTOs.*;
import com.vocadabulary.dto.TemplateDTOs;
import com.vocadabulary.dto.TemplateDTOs.TemplateResponseWithBlank;
import com.vocadabulary.models.*;
import com.vocadabulary.repositories.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Service
public class SentenceService {

    private final SentenceTemplateRepository templateRepo;
    private final SentenceTemplateBlankRepository blankRepo;
    private final UserFlashcardRepository userFlashcardRepo;
    private final FlashcardRepository flashcardRepo;
    private final UserSentenceAttemptRepository attemptRepo;
    private final UserSentenceAttemptFillRepository fillRepo;
    private final UserSentenceBlankStatsRepository statsRepo;
    private final SentenceTemplateRepository sentenceTemplateRepo;
    private final SentenceTemplateBlankRepository sentenceTemplateBlankRepo;
    private final Map<String, Map<Integer, Integer>> sessionFailStreaks = new ConcurrentHashMap<>();


    public SentenceService(
        SentenceTemplateRepository templateRepo,
        SentenceTemplateBlankRepository blankRepo,
        UserFlashcardRepository userFlashcardRepo,
        FlashcardRepository flashcardRepo,
        UserSentenceAttemptRepository attemptRepo,
        UserSentenceAttemptFillRepository fillRepo,
        UserSentenceBlankStatsRepository statsRepo) {
            this.templateRepo = templateRepo;
            this.blankRepo = blankRepo;
            this.userFlashcardRepo = userFlashcardRepo;
            this.flashcardRepo = flashcardRepo;
            this.attemptRepo = attemptRepo;
            this.fillRepo = fillRepo;
            this.statsRepo = statsRepo;
            this.sentenceTemplateRepo = templateRepo;
            this.sentenceTemplateBlankRepo = blankRepo;
        }
    
        private long currentUserId() {
            MockUser mockUser = MockUserContext.getCurrentUser();
            if (mockUser == null) {
                throw new IllegalStateException("No mock user found in context");
            }
            return mockUser.getId();
        }

        private String getSessionKey(Long templateId) {
            return currentUserId() + "-" + templateId;
        }

        private Map<Integer, Integer> getSessionStreaks(Long templateId) {
            String key = getSessionKey(templateId);
            return sessionFailStreaks.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
        }

        public void resetSessionStats(Long templateId) {
            String key = getSessionKey(templateId);
            sessionFailStreaks.remove(key);
            System.out.println("Reset session stats for template: " + templateId + ", user: " + currentUserId());
        }

        private static String normalize(String s) {
            if (s == null) return "";
            // trim, lowercase, collapse internal spaces
            return s.trim().toLowerCase(java.util.Locale.ROOT).replaceAll("\\s+", " ");
        }

        public TemplateResponseWithBlank getRandomTemplateForUser() {
            long currentUserId = currentUserId();
            
            System.out.println("Getting random template for user: " + currentUserId);
            
            // Use the NEW repository method that excludes correctly guessed templates
            List<SentenceTemplate> candidates = templateRepo.findRandomLearnedUnguessedTemplateForUser(currentUserId);
            
            System.out.println("Found " + candidates.size() + " unguessed candidate templates");

            if (candidates.isEmpty()) {
                throw new IllegalStateException("No unguessed templates found for user's learned flashcards.");
            }

            // Pick random template from the candidates
            SentenceTemplate chosen = candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
            System.out.println("Chosen template: " + chosen.getId() + " - " + chosen.getTemplateText());

            // Get blank info
            List<SentenceTemplateBlank> templateBlanks = blankRepo.findByTemplateIdOrderByBlankIndexAsc(chosen.getId());
            if (templateBlanks.isEmpty()) {
                throw new IllegalStateException("No blank found for template: " + chosen.getId());
            }
            
            SentenceTemplateBlank blank = templateBlanks.get(0);

            return new TemplateResponseWithBlank(
                chosen.getId(),
                chosen.getTemplateText(),
                chosen.getSource(),
                chosen.getContextTopic(),
                chosen.getCreatedAt(),
                blank.getTargetFlashcardId(),
                blank.getBlankIndex()
            );
        }

  public PrepareSentenceResponse prepareSentence(Long templateId) {
    SentenceTemplate t = templateRepo.findById(templateId)
        .orElseThrow(() -> new IllegalArgumentException("Template not found: " + templateId));

    List<SentenceTemplateBlank> blanks = blankRepo.findByTemplateIdOrderByBlankIndexAsc(templateId);
    Map<Integer, SentenceTemplateBlank> byIdx = blanks.stream()
        .collect(Collectors.toMap(SentenceTemplateBlank::getBlankIndex, b -> b));

    String[] parts = t.getTemplateText().split("___", -1);
    List<Chunk> chunks = new ArrayList<>();

    for (int i=0; i<parts.length; i++) {
      if (!parts[i].isEmpty()) chunks.add(new Chunk("text", parts[i], null, null, null));
      if (i < parts.length-1) {
        int idx = i;
    
        // Get session streaks for this template
        Map<Integer, Integer> sessionStreaks = getSessionStreaks(templateId);
    
        // Check SESSION fail streak (not persistent stats)
        int sessionFailCount = sessionStreaks.getOrDefault(idx, 0);
        boolean reveal = sessionFailCount >= 3;

        String revealedWord = null;
        SentenceTemplateBlank b = byIdx.get(idx);
        if (reveal && b != null && b.getTargetFlashcardId() != null) {
            revealedWord = flashcardRepo.findWordById(b.getTargetFlashcardId());
        }
        System.out.println("Prepare blank " + idx + ": sessionFailCount=" + sessionFailCount + ", reveal=" + reveal);
        chunks.add(new Chunk("blank", null, idx, reveal, revealedWord));
        }
    }
    return new PrepareSentenceResponse(templateId, chunks);
  }

    @Transactional
    public FinalizeSentenceResponse finalizeSentence(FinalizeSentenceRequest req) {
    Objects.requireNonNull(req.templateId); 
    Objects.requireNonNull(req.answers);

    SentenceTemplate t = templateRepo.findById(req.templateId)
        .orElseThrow(() -> new IllegalArgumentException("Template not found"));

    // Build learned lookup (normalized word -> flashcardId)
    List<UserFlashcard> learnedUF = userFlashcardRepo.findLearnedFlashcards(currentUserId());
    Map<String, Long> learnedByWord = new HashMap<>();
    for (UserFlashcard uf : learnedUF) {
        String w = normalize(uf.getFlashcard().getWord());
        learnedByWord.put(w, uf.getFlashcard().getId());

        // ADD DEBUG LOGGING:
        System.out.println("Added to learned map: '" + w + "' -> " + uf.getFlashcard().getId() + 
            " (original: '" + uf.getFlashcard().getWord() + "')");
    }
    // DEBUG LOGGING
    System.out.println("Total learned words in map: " + learnedByWord.size());

    // Blanks
    List<SentenceTemplateBlank> blanks = blankRepo.findByTemplateIdOrderByBlankIndexAsc(req.templateId);
    Map<Integer, SentenceTemplateBlank> byIdx = blanks.stream()
        .collect(Collectors.toMap(SentenceTemplateBlank::getBlankIndex, b -> b));

    // answers map (lowercase+trim here!)
    Map<Integer, String> typed = new HashMap<>();
    for (FinalizeSentenceRequest.BlankAnswer a : req.answers) {
        String normalized = normalize(a.typedWord);
        typed.put(a.blankIndex, normalized);

        // ADD DEBUG LOGGING:
        System.out.println("Answer for blank " + a.blankIndex + ": '" + a.typedWord + 
                "' -> normalized: '" + normalized + "'");
    }

    String[] parts = t.getTemplateText().split("___", -1);
    int blankCount = Math.max(0, parts.length-1);
    for (int i=0; i<blankCount; i++) {
        if (!typed.containsKey(i)) throw new IllegalArgumentException("Missing answer for blankIndex " + i);
    }

    List<PerBlank> perBlank = new ArrayList<>();
    boolean allCorrect = true;

    // Get session streaks for this template
    Map<Integer, Integer> sessionStreaks = getSessionStreaks(req.templateId);

    // Create local tempFills list
    List<UserSentenceAttemptFill> tempFills = new ArrayList<>();

    // persist attempt shell
    UserSentenceAttempt attempt = new UserSentenceAttempt();
    attempt.setUserId(currentUserId()); 
    attempt.setTemplateId(req.templateId);

    StringBuilder finalText = new StringBuilder();

    for (int i=0; i<parts.length; i++) {
        finalText.append(parts[i]);
        if (i < parts.length-1) {
            int idx = i;
            String ans = typed.get(idx);
            SentenceTemplateBlank b = byIdx.get(idx);

            boolean isCorrect;
            Long matchedId = null;

            if (b != null && b.getTargetFlashcardId() != null) {
                // strict target
                Long targetId = b.getTargetFlashcardId();
                Long candidate = learnedByWord.get(ans);
                isCorrect = (candidate != null && candidate.equals(targetId));
                matchedId = candidate;

                // ADD DEBUG LOGGING:
                String actualWord = flashcardRepo.findWordById(targetId);
                System.out.println("DEBUG - Blank " + idx + ":");
                System.out.println("  User typed (raw): '" + req.answers.get(idx).typedWord + "'");
                System.out.println("  User typed (normalized): '" + ans + "'");
                System.out.println("  Expected word (raw): '" + actualWord + "'");
                System.out.println("  Expected word (normalized): '" + normalize(actualWord) + "'");
                System.out.println("  Target flashcard ID: " + targetId);
                System.out.println("  Candidate ID from map: " + candidate);
                System.out.println("  Learned words map: " + learnedByWord);
                System.out.println("  Is correct: " + isCorrect);

            } else {
                // any learned word qualifies
                matchedId = learnedByWord.get(ans);
                isCorrect = (matchedId != null);

                // ADD DEBUG LOGGING:
                System.out.println("DEBUG - Blank " + idx + " (any word):");
                System.out.println("  User typed (normalized): '" + ans + "'");
                System.out.println("  Matched ID: " + matchedId);
                System.out.println("  Available learned words: " + learnedByWord.keySet());
            }

            // Update PERSISTENT stats (never reset)
            UserSentenceBlankStats.Key key = new UserSentenceBlankStats.Key(currentUserId(), req.templateId, idx);
            UserSentenceBlankStats stats = statsRepo.findById(key)
                .orElseGet(() -> new UserSentenceBlankStats(currentUserId(), req.templateId, idx));

            if (isCorrect) {
                stats.setTotalCorrect(stats.getTotalCorrect()+1);
                // Reset SESSION fail streak on correct answer
                sessionStreaks.put(idx, 0);
            } else {
                stats.setTotalIncorrect(stats.getTotalIncorrect()+1);
                // Increment SESSION fail streak on wrong answer
                int currentSessionFails = sessionStreaks.getOrDefault(idx, 0);
                sessionStreaks.put(idx, currentSessionFails + 1);
            }

            // DON'T update persistent failStreak - we're using session-based revealing
            stats.setLastAttemptAt(Instant.now());
            statsRepo.save(stats);

            // Use SESSION fail streak for revealing
            int sessionFailCount = sessionStreaks.get(idx);
            boolean reveal = sessionFailCount >= 3;

            String revealedWord = (b != null && b.getTargetFlashcardId() != null)
                ? flashcardRepo.findWordById(b.getTargetFlashcardId())
                : null;

            System.out.println("Finalize blank " + idx + ": isCorrect=" + isCorrect + 
                            ", sessionFailCount=" + sessionFailCount + ", reveal=" + reveal);

            perBlank.add(new PerBlank(idx, isCorrect, reveal, revealedWord));

            // persist fill
            UserSentenceAttemptFill fill = new UserSentenceAttemptFill();
            fill.setBlankIndex(idx);
            fill.setTypedWord(ans);
            fill.setMatchedFlashcardId(matchedId);
            fill.setIsCorrect(isCorrect);
            tempFills.add(fill);

            if (!isCorrect) allCorrect = false;

            finalText.append(ans);
        }
    }

    attempt.setFinalText(finalText.toString());
    attempt.setCorrect(allCorrect);
    attempt.setCreatedAt(Instant.now());
    attempt = attemptRepo.save(attempt);

    // now save fills with attemptId
    for (UserSentenceAttemptFill f : tempFills) {
        f.setAttemptId(attempt.getId());
        fillRepo.save(f);
    }

    return new FinalizeSentenceResponse(attempt.getId(), attempt.getFinalText(), allCorrect, perBlank);

}
}
