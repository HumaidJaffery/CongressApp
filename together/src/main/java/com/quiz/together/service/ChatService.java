package com.quiz.together.service;

import com.quiz.together.Repository.ChatRepository;
import com.quiz.together.Repository.MentionRepository;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.entity.Chat;
import com.quiz.together.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private RoomRepository roomRepository;


    @Autowired
    private MentionRepository mentionRepository;

    public Chat createChat(Integer roomKey){
        Chat chat = new Chat();
        chat.setRoom(roomRepository.getReferenceById(roomKey));
        return chatRepository.save(chat);
    }

}
