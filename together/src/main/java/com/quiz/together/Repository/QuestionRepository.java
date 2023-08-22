package com.quiz.together.Repository;

import com.quiz.together.entity.Question;
import com.quiz.together.entity.Room;
import com.quiz.together.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository  extends JpaRepository<Question, Long> {
    List<Question> findAllByAuthorAndRoom(User user, Room room);
}
