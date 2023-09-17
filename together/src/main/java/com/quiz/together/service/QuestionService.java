package com.quiz.together.service;

import com.quiz.together.Model.QuestionModel;
import com.quiz.together.Repository.QuestionRepository;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.Repository.UserRepository;
import com.quiz.together.entity.Question;
import com.quiz.together.entity.Room;
import com.quiz.together.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionService {

    @Autowired
    private UserRoomService userRoomService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;


    public Question addQuestion(QuestionModel questionModel) throws Exception {
        Question question = new Question();
        question.setQuestionType(questionModel.getQuestionType());
        question.setRoom(roomRepository.findById(questionModel.getRoomKey()).orElseThrow(() -> new Exception("Room doesn't exist")));
        question.setAuthor(userRepository.findById(questionModel.getUserId()).orElseThrow(() -> new Exception("User doesn't exist")));
        question.setQuestion(questionModel.getQuestion());
        question.setTopics(questionModel.getTopics());
        question.setAnswers(questionModel.getAnswers());
        question.setCorrectAnswer(questionModel.getCorrectAnswer());
        return questionRepository.save(question);
    }

    public Question updateQuestion(QuestionModel questionModel, long questionId){
        Question question = new Question();
        question.setId(questionId);
        question.setQuestionType(questionModel.getQuestionType());
        question.setRoom(roomRepository.getReferenceById(questionModel.getRoomKey()));
        question.setAuthor(userRepository.getReferenceById(questionModel.getUserId()));
        question.setQuestion(questionModel.getQuestion());
        question.setTopics(questionModel.getTopics());
        question.setAnswers(questionModel.getAnswers());
        question.setCorrectAnswer(questionModel.getCorrectAnswer());
        return questionRepository.save(question);
    }

    public void deleteQuestion(long question_id){
        questionRepository.deleteById(question_id);
    }

    public List<Question> getQuestionsFromUserandRoom(long userId, String roomKey) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        Room room = roomRepository.findById(roomKey).orElseThrow(() -> new Exception("room not found"));

        return questionRepository.findAllByAuthorAndRoom(user, room);
    }
}
