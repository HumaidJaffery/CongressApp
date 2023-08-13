package com.quiz.together.Repository;

import com.quiz.together.entity.Chat;
import com.quiz.together.entity.Message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByChat(Chat chat, PageRequest pageable);
}
