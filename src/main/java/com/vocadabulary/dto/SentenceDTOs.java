// SentenceDTOs.java
package com.vocadabulary.dto;

import java.util.List;

public class SentenceDTOs {
  // Prepare
  public static class PrepareSentenceResponse {
    public Long templateId;
    public List<Chunk> chunks;
    public PrepareSentenceResponse(Long templateId, List<Chunk> chunks) {
      this.templateId = templateId; this.chunks = chunks;
    }
  }
  public static class Chunk {
    public String type;           // "text" | "blank"
    public String value;          // for text
    public Integer blankIndex;    // for blank
    public Boolean reveal;        // for blank
    public String revealedWord;   // nullable
    public Chunk(String type, String value, Integer blankIndex, Boolean reveal, String revealedWord){
      this.type=type; this.value=value; this.blankIndex=blankIndex; this.reveal=reveal; this.revealedWord=revealedWord;
    }
  }

  // Finalize
  public static class FinalizeSentenceRequest {
    public Long templateId;
    public List<BlankAnswer> answers;
    public static class BlankAnswer { public int blankIndex; public String typedWord; }
  }

  public static class FinalizeSentenceResponse {
    public Long attemptId;
    public String finalText;
    public boolean allCorrect;
    public List<PerBlank> perBlank;
    public FinalizeSentenceResponse(Long attemptId, String finalText, boolean allCorrect, List<PerBlank> perBlank){
      this.attemptId=attemptId; this.finalText=finalText; this.allCorrect=allCorrect; this.perBlank=perBlank;
    }
  }
  public static class PerBlank {
    public int blankIndex; public boolean isCorrect; public boolean reveal; public String revealedWord;
    public PerBlank(int blankIndex, boolean isCorrect, boolean reveal, String revealedWord){
      this.blankIndex=blankIndex; this.isCorrect=isCorrect; this.reveal=reveal; this.revealedWord=revealedWord;
    }
  }
}
