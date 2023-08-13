package com.quiz.together.Repository;

import com.quiz.together.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository  extends JpaRepository<Question, Long> {
}
