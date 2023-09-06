package com.quiz.together.controller;

import com.quiz.together.Model.MessageModel;
import com.quiz.together.entity.Message;
import com.quiz.together.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageController {
    @Autowired
    public MessageService messageService;

    @PostMapping("/add")
    public Message addMessage(@RequestBody MessageModel messageModel){
        return messageService.addMessage(messageModel);
    }

}
