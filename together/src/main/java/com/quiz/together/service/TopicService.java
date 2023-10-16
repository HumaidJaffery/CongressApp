package com.quiz.together.service;

import com.quiz.together.Model.TopicModel;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.Repository.TopicRepository;
import com.quiz.together.entity.Question;
import com.quiz.together.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Topic addTopic(TopicModel topicModel){
        Topic topic = new Topic();
        topic.setName(topicModel.getName());
        return topicRepository.save(topic);
    }

    public void deleteTopic(long topicId){
        topicRepository.deleteById(topicId);
    }

    public void addQuestionToTopic(Question question, long topicId){
        Topic topic = topicRepository.getReferenceById(topicId);
        List<Question> questions = topic.getQuestions();
        questions.add(question);
        topic.setQuestions(questions);
        topicRepository.save(topic);
    }


}
