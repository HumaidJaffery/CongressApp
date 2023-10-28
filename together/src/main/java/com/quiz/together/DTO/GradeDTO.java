package com.quiz.together.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.quiz.together.entity.GradedQuestion;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GradeDTO {
    private List<GradedQuestion> gradedQuestions;

    private String submissionTime;

    private double percentage;
}
