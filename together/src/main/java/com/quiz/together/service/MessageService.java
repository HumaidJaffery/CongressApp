package com.quiz.together.service;

import com.quiz.together.Repository.ChatRepository;
import com.quiz.together.Repository.MessageRepository;
import com.quiz.together.Repository.UserRepository;
import com.quiz.together.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public Message addMessage(long chat_id, long user_id, String text, Date date){
        Message message = new Message();
        message.setChat(chatRepository.getReferenceById(chat_id));
        message.setUser(userRepository.getReferenceById(user_id));
        message.setText(text);
        message.setDate(date);
        return messageRepository.save(message);
    }

    private Page<Message> getMessages(long chat_id, int page){
        System.out.println(chatRepository.getReferenceById(chat_id).getMessages());
        PageRequest pr = PageRequest.of(page, 10);
        return messageRepository.findAllByChat(chatRepository.getReferenceById(chat_id), pr);
    }

}
