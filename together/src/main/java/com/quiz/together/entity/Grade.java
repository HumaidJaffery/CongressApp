package com.quiz.together.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    @Id
    @SequenceGenerator(sequenceName = "id", name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private long id;

    @OneToMany
    @JoinColumn
    @JsonManagedReference
    private List<GradedQuestion> gradedQuestions;

    private Integer percentage;
}
