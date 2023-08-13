package com.quiz.together.service;

import com.quiz.together.Repository.MentionRepository;
import com.quiz.together.Repository.MessageRepository;
import com.quiz.together.entity.Mention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentionService {

    @Autowired
    private MentionRepository mentionRepository;

    @Autowired
    private MessageRepository messageRepository;



//    private Mention addMention(long message_id, long user_id){
//        Mention mention = new Mention();
//        mention.setMessage(messageRepository.getReferenceById(message_id));
//        mention.setUser(userRepository.getReferenceById(user_id));
//        mention.setRead(false);
//        return mentionRepository.save(mention);
//    }
//
//    private List<Mention> getMentions(long user_id){
//        return mentionRepository.findAllByUser(userRepository.getReferenceById(user_id));
//    }
//
//    private List<Mention> getUnreadMentions(long user_id){
//        return mentionRepository.findAllByUserAndRead(userRepository.getReferenceById(user_id), false);
//    }
//
//    private Mention changeToRead(long mention_id){
//        Mention mention = mentionRepository.getReferenceById(mention_id);
//        mention.setRead(true);
//        return mentionRepository.save(mention);
//    }

}
