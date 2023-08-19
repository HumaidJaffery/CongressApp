package com.quiz.together.controller;

import com.quiz.together.Model.MentionModel;
import com.quiz.together.entity.Mention;
import com.quiz.together.service.MentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("Mention/")
@CrossOrigin
public class MentionController {
    @Autowired
    public MentionService mentionService;

    @PostMapping("add/")
    public Mention addMention(@RequestBody MentionModel mentionModel){
        return mentionService.addMention(mentionModel);
    }

    @GetMapping("getAll/{user_id}")
    public List<Mention> getMentions(@PathVariable long user_id){
        return mentionService.getMentions(user_id);
    }

    @GetMapping("getUnread/{user_id}")
    public List<Mention> getUnreadMentions(@PathVariable long user_id){
        return mentionService.getUnreadMentions(user_id);
    }

    @PutMapping("changeToRead/{mention_id}")
    public Mention changeToRead(@PathVariable long mention_id){
        return mentionService.changeToRead(mention_id);
    }


}
