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
        UserSentenceBlankStats.Key key = new UserSentenceBlankStats.Key(currentUserId(), templateId, idx);
        Optional<UserSentenceBlankStats> stat = statsRepo.findById(key);
        boolean reveal = stat.map(s -> s.getFailStreak() >= 3).orElse(false);

        String revealedWord = null;
        SentenceTemplateBlank b = byIdx.get(idx);
        if (reveal && b != null && b.getTargetFlashcardId() != null) {
          // You can inject FlashcardRepository if you want exact word here; or resolve at finalize
        revealedWord = flashcardRepo.findWordById(b.getTargetFlashcardId());
        }
        chunks.add(new Chunk("blank", null, idx, reveal, revealedWord));
      }
    }
    return new PrepareSentenceResponse(templateId, chunks);
  }

  @Transactional
  public FinalizeSentenceResponse finalizeSentence(FinalizeSentenceRequest req) {
    Objects.requireNonNull(req.templateId); Objects.requireNonNull(req.answers);

    SentenceTemplate t = templateRepo.findById(req.templateId)
        .orElseThrow(() -> new IllegalArgumentException("Template not found"));

    // Build learned lookup (normalized word -> flashcardId)
    List<UserFlashcard> learnedUF = userFlashcardRepo.findLearnedFlashcards(currentUserId());
    Map<String, Long> learnedByWord = new HashMap<>();
    for (UserFlashcard uf : learnedUF) {
        String w = normalize(uf.getFlashcard().getWord());
        learnedByWord.put(w, uf.getFlashcard().getId());
    }

    // Blanks
    List<SentenceTemplateBlank> blanks = blankRepo.findByTemplateIdOrderByBlankIndexAsc(req.templateId);
    Map<Integer, SentenceTemplateBlank> byIdx = blanks.stream()
        .collect(Collectors.toMap(SentenceTemplateBlank::getBlankIndex, b -> b));

    // answers map (lowercase+trim here!)
    Map<Integer, String> typed = new HashMap<>();
    for (FinalizeSentenceRequest.BlankAnswer a : req.answers) {
        typed.put(a.blankIndex, normalize(a.typedWord));
    }

    String[] parts = t.getTemplateText().split("___", -1);
    int blankCount = Math.max(0, parts.length-1);
    for (int i=0; i<blankCount; i++) {
        if (!typed.containsKey(i)) throw new IllegalArgumentException("Missing answer for blankIndex " + i);
    }

    List<PerBlank> perBlank = new ArrayList<>();
    boolean allCorrect = true;

    // persist attempt shell
    UserSentenceAttempt attempt = new UserSentenceAttempt();
    attempt.setUserId(currentUserId()); attempt.setTemplateId(req.templateId);

    // Build final text with original casing as typed (we can keep normalized for checks but store original)
    // We only stored normalized in 'typed'; to keep original, also build a second map if you want.
    // For now, final text will use the normalized (lowercased) typed words per your rule.
    StringBuilder finalText = new StringBuilder();

    for (int i=0; i<parts.length; i++) {
        finalText.append(parts[i]);
        if (i < parts.length-1) {
            int idx = i;
            String ans = typed.get(idx); // already lower-cased+trimmed
            SentenceTemplateBlank b = byIdx.get(idx);

        boolean isCorrect;
        Long matchedId = null;

        if (b != null && b.getTargetFlashcardId() != null) {
            // strict target
            Long targetId = b.getTargetFlashcardId();
            Long candidate = learnedByWord.get(ans); // must be learned and match word
            isCorrect = (candidate != null && candidate.equals(targetId));
            matchedId = candidate;
        } else {
            // any learned word qualifies
            matchedId = learnedByWord.get(ans);
            isCorrect = (matchedId != null);
        }

        // stats update
        UserSentenceBlankStats.Key key = new UserSentenceBlankStats.Key(currentUserId(), req.templateId, idx);
        UserSentenceBlankStats stats = statsRepo.findById(key)
            .orElseGet(() -> new UserSentenceBlankStats(currentUserId(), req.templateId, idx));

        if (isCorrect) {
            stats.setTotalCorrect(stats.getTotalCorrect()+1);
            stats.setFailStreak(0);
        } else {
            stats.setTotalIncorrect(stats.getTotalIncorrect()+1);
            stats.setFailStreak(stats.getFailStreak()+1);
        }
        stats.setLastAttemptAt(Instant.now());
        statsRepo.save(stats);

        boolean reveal = stats.getFailStreak() >= 3;
        String revealedWord = (b != null && b.getTargetFlashcardId() != null)
            ? flashcardRepo.findWordById(b.getTargetFlashcardId())
            : null;
        perBlank.add(new PerBlank(idx, isCorrect, reveal, revealedWord));

        // persist fill
        UserSentenceAttemptFill fill = new UserSentenceAttemptFill();
        // attempt id not yet available; will set after saving attempt
        fill.setBlankIndex(idx);
        fill.setTypedWord(ans);
        fill.setMatchedFlashcardId(matchedId);
        fill.setIsCorrect(isCorrect);
        // store later via helper list
        tempFills.add(fill);

        if (!isCorrect) allCorrect = false;

        // final text append the user's entry (lowercased+trimmed as requested)
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

    private static String normalize(String s) {
        if (s == null) return "";
        // trim, lowercase, collapse internal spaces
        return s.trim().toLowerCase(java.util.Locale.ROOT).replaceAll("\\s+", " ");
    }

    // local cache for fills per request
    private final List<UserSentenceAttemptFill> tempFills = new ArrayList<>();

    // Get a random template for a user
    public TemplateResponseWithBlank getRandomTemplateForUser(Long currentUserId) {
        // 1. Get all learned & visible flashcards
        List<UserFlashcard> learnedFlashcards = userFlashcardRepo.findLearnedAndVisibleFlashcards(currentUserId());

        if (learnedFlashcards.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no learned flashcards. Learn some first.");
        }
        // 2. Get all flashcard IDs
        List<Long> flashcardIds = learnedFlashcards.stream()
            .map(uf -> uf.getFlashcard().getId())
            .toList();

        // 3. Find matching templates
        List<SentenceTemplate> candidates = sentenceTemplateRepo.findTemplatesWithFlashcardIds(flashcardIds);

        if (candidates.isEmpty()) {
            throw new IllegalStateException("No templates found for user's learned flashcards.");
        }

        // 4. Pick one at random
        SentenceTemplate chosen = candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));

        // 5. Also get the blank to know which flashcard it links to
        SentenceTemplateBlank blank = sentenceTemplateBlankRepo.findByTemplateId(chosen.getId());
        if (blank == null) {
            throw new IllegalStateException("No blanks found for the selected template.");
        }

        return new TemplateDTOs.TemplateResponseWithBlank(
            chosen.getId(),
            chosen.getTemplateText(),
            chosen.getSource(),
            chosen.getContextTopic(),
            chosen.getCreatedAt(),
            blank.getTargetFlashcardId(),
            blank.getBlankIndex()
        );
    }
}
