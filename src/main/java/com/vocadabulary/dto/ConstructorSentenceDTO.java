package com.vocadabulary.dto;

import java.util.List;

public class ConstructorSentenceDTO {
    public Long sentenceId;
    public String sentenceTemplate;
    public List<BlankDTO> blanks;

    public static class BlankDTO {
        public int index;
        public Long flashcardId;
    }
}
