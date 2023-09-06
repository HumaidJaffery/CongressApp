package com.quiz.together.controller;

import com.quiz.together.Model.QuestionModel;
import com.quiz.together.entity.Question;
import com.quiz.together.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/getQuestions/{userId}/{roomKey}")
    public List<Question> getUserQuestionsFromSpecificRoom(@PathVariable long userId, @PathVariable Integer roomKey) throws Exception {
        return questionService.getQuestionsFromUserandRoom(userId, roomKey);
    }

    @PostMapping("/add")
    public Question addQuestion(@RequestBody QuestionModel questionModel) throws Exception {
        return questionService.addQuestion(questionModel);
    }

    @PutMapping("/update/{question_id}")
    public Question updateQuestion(@RequestBody QuestionModel questionModel, @PathVariable long question_id){
        return questionService.updateQuestion(questionModel, question_id);
    }

    @DeleteMapping("/delete")
    public void deleteQuestion(@RequestBody long questionId){
        questionService.deleteQuestion(questionId);
    }


}
