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



    public Mention addMention(MentionModel mentionModel){
        Mention mention = new Mention();
        mention.setMessage(messageRepository.getReferenceById(mentionModel.getMessageId()));
        mention.setUser(userRepository.getReferenceById(mentionModel.getChatId()));
        mention.setRead(false);
        return mentionRepository.save(mention);
    }

    public List<Mention> getMentions(long user_id){
        return mentionRepository.findAllByUser(userRepository.getReferenceById(user_id));
    }

    public List<Mention> getUnreadMentions(long user_id){
        return mentionRepository.findAllByUserAndRead(userRepository.getReferenceById(user_id), false);
    }

    public Mention changeToRead(long mention_id){
        Mention mention = mentionRepository.getReferenceById(mention_id);
        mention.setRead(true);
        return mentionRepository.save(mention);
    }

}
