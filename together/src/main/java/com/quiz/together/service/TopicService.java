package com.quiz.together.service;

import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.Repository.TopicRepository;
import com.quiz.together.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Topic addTopic(String name, Integer room_id){
        Topic topic = new Topic();
        topic.setName(name);
        topic.setRoom(roomRepository.getReferenceById(room_id));
        return topicRepository.save(topic);
    }

    private void deleteTopic(long topicId){
        topicRepository.deleteById(topicId);
    }


}
