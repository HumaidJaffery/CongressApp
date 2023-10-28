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

import java.util.*;

@Repository
public class QuestionService {

    @Autowired
    private UserRoomService userRoomService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired RoomService roomService;


    public Question addQuestion(QuestionModel questionModel) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Question question = new Question();
        question.setQuestionType(questionModel.getQuestionType());
        question.setRoom(roomRepository.findById(questionModel.getRoomKey()).orElseThrow(() -> new Exception("Room doesn't exist")));
        question.setAuthor(user);
        question.setQuestion(questionModel.getQuestion());
        question.setExplanation(questionModel.getExplanation());
        question.setAmountTimesAnsweredCorrect(0);
        question.setAmountTimesAnswered(0);

        //adding list of topics to question
        List<Topic> topics = new ArrayList<>();
        for(int i =0; i< questionModel.getTopicIds().size(); i++){
            topics.add(topicRepository.getReferenceById(questionModel.getTopicIds().get(i)));
        }
        question.setTopics(topics);
        question.setAnswers(questionModel.getAnswers());
        question.setCorrectAnswer(questionModel.getCorrectAnswer());
        Question response = questionRepository.save(question);

        //updating userRoomRelation
        userRoomService.userCreateQuestion(questionModel.getRoomKey(), user.getEmail(), response);

        //adding question to Topic
        for(int i=0; i<questionModel.getTopicIds().size(); i++){
            topicService.addQuestionToTopic(response, questionModel.getTopicIds().get(i));
        }

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
        System.out.println("in delete question service");
        questionRepository.deleteById(question_id);
    }

    public List<Question> getQuestionsFromUserandRoom(String userEmail, String roomKey) throws Exception {
        User user = userRepository.findById(userEmail).orElseThrow(() -> new Exception("User not found"));
        Room room = roomRepository.findById(roomKey).orElseThrow(() -> new Exception("room not found"));
        return questionRepository.findAllByAuthorAndRoom(user, room);
    }

    public Set<Question> getQuestions(String roomKey, int numOfQuestions) throws Exception {
        Optional<Room> room = roomRepository.findById(roomKey);
        if(!room.isPresent()) throw new Exception("Room doesn't exist");

        Set<Question> questions = new HashSet<>();
        //getting list of topics
        List<Topic> topics =  room.get().getTopics();

        //if there are no topics get random questions
        List<Question> allQuestions = room.get().getQuestions();
        if(topics.size()  < 2){
            Random random = new Random();
            while(questions.size() < numOfQuestions){
                System.out.println(allQuestions.size() + "------");
                if(allQuestions.size() == 1) {
                    questions.add(allQuestions.get(0));
                    break;
                }
                int index = random.nextInt(0, allQuestions.size()-1);
                System.out.println(index);
                questions.add(allQuestions.get(index));
                allQuestions.remove(index);
            }
            return questions;
        }

        //creating hashmap of topicId's to list of Questions
        HashMap<Long, List<Question>> questionsInTopics = new HashMap<>();
        for(int i=0; i<topics.size(); i++){
            questionsInTopics.put(topics.get(i).getId(), topics.get(i).getQuestions());
        }

        //going through each topic, one-by-one, and adding one question to set
        int i = 0;
        while(questions.size() < numOfQuestions){
            //All the questions in one current topic
            List<Question> currQuestions = questionsInTopics.get( topics.get(i).getId());
            if(currQuestions != null && currQuestions.size() > 0){
                System.out.println("==" + currQuestions.get(currQuestions.size()-1).getQuestion());
                //add last question in Topic to questions
                questions.add(currQuestions.get(currQuestions.size()-1));
                //remove the question from hashmap
                currQuestions.remove(currQuestions.size()-1);
            }
            i++;
            if(i == topics.size()) i = 0;
        }

        System.out.println(questions);
        return questions;
    }

}
