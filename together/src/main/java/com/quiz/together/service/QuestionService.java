package com.quiz.together.service;

import com.quiz.together.Model.QuestionModel;
import com.quiz.together.Repository.QuestionRepository;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.Repository.TopicRepository;
import com.quiz.together.Repository.UserRepository;
import com.quiz.together.entity.Question;
import com.quiz.together.entity.Room;
import com.quiz.together.entity.Topic;
import com.quiz.together.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.ErrorResponseException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionService {

    @Autowired
    private UserRoomService userRoomService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;


    public Question addQuestion(QuestionModel questionModel) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Question question = new Question();
        question.setQuestionType(questionModel.getQuestionType());
        question.setRoom(roomRepository.findById(questionModel.getRoomKey()).orElseThrow(() -> new Exception("Room doesn't exist")));
        question.setAuthor(user);
        question.setQuestion(questionModel.getQuestion());
        question.setExplanation(questionModel.getQuestion());

        List<Topic> topics = new ArrayList<>();
        for(int i =0; i< questionModel.getTopicIds().size(); i++){
            topics.add(topicRepository.getReferenceById(questionModel.getTopicIds().get(i)));
        }
        question.setTopics(topics);
        question.setAnswers(questionModel.getAnswers());
        question.setCorrectAnswer(questionModel.getCorrectAnswer());
        Question response = questionRepository.save(question);
        userRoomService.userCreateQuestion(questionModel.getRoomKey(), user.getId(), response);
        return response;
    }

    public Question updateQuestion(QuestionModel questionModel, long questionId) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null) throw new Exception("Invalid Jwt Token, user does not exist");

        Question question = new Question();
        question.setId(questionId);
        question.setQuestionType(questionModel.getQuestionType());
        question.setRoom(roomRepository.getReferenceById(questionModel.getRoomKey()));
        question.setAuthor(user);
        question.setQuestion(questionModel.getQuestion());
        List<Topic> topics = new ArrayList<>();
        for(int i =0; i< questionModel.getTopicIds().size(); i++){
            topics.add(topicRepository.getReferenceById(questionModel.getTopicIds().get(i)));
        }
        question.setTopics(topics);
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
