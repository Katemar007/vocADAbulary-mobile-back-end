package com.vocadabulary.models;

import jakarta.persistence.*;

@Entity
@Table(name = "constructor_sentence_blanks")
public class ConstructorSentenceBlank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int orderIndex;

    @ManyToOne
    @JoinColumn(name = "sentence_id", nullable = false)
    private ConstructorSentence sentence;

    @ManyToOne
    @JoinColumn(name = "flashcard_id", nullable = false)
    private Flashcard flashcard;

    public int getOrderIndex() { return orderIndex; }
    public Flashcard getFlashcard() { return flashcard; }
}
