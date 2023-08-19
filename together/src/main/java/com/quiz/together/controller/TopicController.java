package com.quiz.together.controller;

import com.quiz.together.Model.TopicModel;
import com.quiz.together.entity.Topic;
import com.quiz.together.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("topic/")
@CrossOrigin
public class TopicController {
    @Autowired
    public TopicService topicService;

    @PostMapping("add/")
    public Topic addTopic(@RequestBody TopicModel topicModel){
        return topicService.addTopic(topicModel);
    }

    @DeleteMapping("delete/{topic_id}")
    public void deleteTopic(@PathVariable long topic_id){
        topicService.deleteTopic(topic_id);
    }
}
