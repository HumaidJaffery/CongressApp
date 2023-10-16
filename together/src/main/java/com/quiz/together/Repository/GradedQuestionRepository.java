package com.quiz.together.Repository;

import com.quiz.together.entity.Grade;
import com.quiz.together.entity.GradedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradedQuestionRepository extends JpaRepository<GradedQuestion, Long> {
}
