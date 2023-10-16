package com.quiz.together.controller;

import com.quiz.together.Model.QuestionModel;
import com.quiz.together.entity.Question;
import com.quiz.together.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/get/{roomKey}/{numOfQuestions}")
    public Set<Question> getQuestions(@PathVariable String roomKey, @PathVariable int numOfQuestions) throws Exception {
        return questionService.getQuestions(roomKey, numOfQuestions);
    }

    @GetMapping("/getQuestionsFromUser/{userEmail}/{roomKey}")
    public List<Question> getUserQuestionsFromSpecificRoom(@PathVariable String userEmail, @PathVariable String roomKey) throws Exception {
        return questionService.getQuestionsFromUserandRoom(userEmail, roomKey);
    }

    @PostMapping("/add")
    public void addQuestion(@RequestBody QuestionModel questionModel) throws Exception {
        questionService.addQuestion(questionModel);
    }

    @PutMapping("/update/{question_id}")
    public Question updateQuestion(@RequestBody QuestionModel questionModel, @PathVariable long question_id) throws Exception {
        return questionService.updateQuestion(questionModel, question_id);
    }

    @DeleteMapping("/delete")
    public void deleteQuestion(@RequestBody long questionId){
        questionService.deleteQuestion(questionId);
    }


}
