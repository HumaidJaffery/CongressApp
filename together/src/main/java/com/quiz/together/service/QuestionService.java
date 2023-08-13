package com.quiz.together.service;

import com.quiz.together.Model.QuestionModel;
import com.quiz.together.Repository.QuestionRepository;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RoomRepository roomRepository;

//    @Autowired
//    private UserRepository userRepository;


    private Question addQuestion(QuestionModel questionModel){
        Question question = new Question();
        question.setQuestionType(questionModel.getQuestionType());
        question.setRoom(roomRepository.getReferenceById(questionModel.getRoom_key()));
//        question.setAuthor(userRepository.getReferenceById(questionModel.getUser_id()));
        question.setQuestion(questionModel.getQuestion());
        question.setTopics(questionModel.getTopics());
        question.setAnswers(questionModel.getAnswers());
        question.setCorrectAnswer(questionModel.getCorrectAnswer());
        return questionRepository.save(question);
    }

    private void deleteQuestion(long question_id){
        questionRepository.deleteById(question_id);
    }
}
