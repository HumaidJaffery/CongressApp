package com.quiz.together.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gradedQuestion")
public class GradedQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String question;
    private List<String> answers;
    private String selectedAnswer;
    private String correctAnswer;
    private boolean isCorrect;
    private String explanation;
}
