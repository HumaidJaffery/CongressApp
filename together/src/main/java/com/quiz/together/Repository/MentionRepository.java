package com.quiz.together.Repository;

import com.quiz.together.entity.Mention;
import com.quiz.together.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MentionRepository extends JpaRepository<Mention, Long> {
    List<Mention> findAllByUser(User user);

    List<Mention> findAllByUserAndRead(User user, boolean read);
}
