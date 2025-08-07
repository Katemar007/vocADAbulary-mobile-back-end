// UserSentenceBlankStats.java
package com.vocadabulary.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity @Table(name = "user_sentence_blank_stats")
@IdClass(UserSentenceBlankStats.Key.class)
public class UserSentenceBlankStats {
    @Id private Long userId;
    @Id private Long templateId;
    @Id private Integer blankIndex;

    private int totalCorrect;
    private int totalIncorrect;
    private int failStreak;
    private Instant lastAttemptAt;

        // ✅ Constructor initializes all ID fields safely
    public UserSentenceBlankStats(Long userId, Long templateId, Integer blankIndex) {
        this.userId = userId;
        this.templateId = templateId;
        this.blankIndex = blankIndex;
        this.totalCorrect = 0;
        this.totalIncorrect = 0;
        this.failStreak = 0;
    }

    // ✅ Required no-arg constructor for JPA
    public UserSentenceBlankStats() {}

    public static class Key implements Serializable {
        private Long userId; 
        private Long templateId; 
        private Integer blankIndex;

        public Key() {}

        public Key(Long u, Long t, Integer b) {
            userId=u; 
            templateId=t; 
            blankIndex=b; 
        }
        // @Override 
        // public boolean equals(Object o){ if(this==o) return true;
        //     if(!(o instanceof Key k)) return false;
        //     return Objects.equals(userId,k.userId)&&Objects.equals(templateId,k.templateId)&&Objects.equals(blankIndex,k.blankIndex);}
        @Override public int hashCode(){ 
            return Objects.hash(userId,templateId,blankIndex);}
        }
        // getters/setters

        public Long getUserId() {
            return userId;
        }
        
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        
        public Long getTemplateId() {
            return templateId;
        }
        
        public void setTemplateId(Long templateId) {
            this.templateId = templateId;
        }
        
        public Integer getBlankIndex() {
            return blankIndex;
        }
        
        public void setBlankIndex(Integer blankIndex) {
            this.blankIndex = blankIndex;
        }
        
        public int getTotalCorrect() {
            return totalCorrect;
        }
        
        public void setTotalCorrect(int totalCorrect) {
            this.totalCorrect = totalCorrect;
        }
        
        public int getTotalIncorrect() {
            return totalIncorrect;
        }
        
        public void setTotalIncorrect(int totalIncorrect) {
            this.totalIncorrect = totalIncorrect;
        }
        
        public int getFailStreak() {
            return failStreak;
        }
        
        public void setFailStreak(int failStreak) {
            this.failStreak = failStreak;
        }
        
        public Instant getLastAttemptAt() {
            return lastAttemptAt;
        }
        
        public void setLastAttemptAt(Instant lastAttemptAt) {
            this.lastAttemptAt = lastAttemptAt;
        }
}