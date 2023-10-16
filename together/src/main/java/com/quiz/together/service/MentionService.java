package com.quiz.together.service;

import com.quiz.together.Model.MentionModel;
import com.quiz.together.Repository.MentionRepository;
import com.quiz.together.Repository.MessageRepository;
import com.quiz.together.Repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;



//    public Mention addMention(MentionModel mentionModel){
//        Mention mention = new Mention();
//        mention.setMessage(messageRepository.getReferenceById(mentionModel.getMessageId()));
//        mention.setUser(userRepository.getReferenceById();
//        mention.setRead(false);
//        return mentionRepository.save(mention);
//    }
//
//    public List<Mention> getMentions(String userEmail){
//        return mentionRepository.findAllByUser(userRepository.getReferenceById(userEmail));
//    }
//
//    public List<Mention> getUnreadMentions(String userEmail){
//        return mentionRepository.findAllByUserAndRead(userRepository.getReferenceById(userEmail), false);
//    }
//
//    public Mention changeToRead(long mention_id){
//        Mention mention = mentionRepository.getReferenceById(mention_id);
//        mention.setRead(true);
//        return mentionRepository.save(mention);
//    }

}
