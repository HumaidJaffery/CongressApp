package com.quiz.together.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
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

    private String submissionTime;

    private double percentage;
}
