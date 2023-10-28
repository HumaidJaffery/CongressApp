package com.quiz.together.service;

import com.quiz.together.DTO.GradeDTO;
import com.quiz.together.DTO.UserRoomStatisticsDTO;
import com.quiz.together.Enum.UserStatus;
import com.quiz.together.Model.SubmittedQuestion;
import com.quiz.together.Repository.*;
import com.quiz.together.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserRoomService {

        @Autowired
        private UserRoomRelationRepository userRoomRelationRepository;

        @Autowired
        private RoomRepository roomRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private QuestionRepository questionRepository;

        @Autowired
        private GradeRepository gradeRepository;

        @Autowired
        private GradedQuestionRepository gradedQuestionRepository;


        public UserRoomRelation userJoinRoom(String roomKey, String userEmail){
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
                if(userRoomRelationRepository.findById(roomKey + user.getId()).isPresent()) return userRoomRelationRepository.getReferenceById(roomKey + user.getId());
                UserRoomRelation userRoomRelation = new UserRoomRelation();

                userRoomRelation.setId(roomKey + "" + user.getId());

                //adding participant count
                Room room = roomRepository.getReferenceById(roomKey);
                room.setLikes(room.getLikes()+1);
                userRoomRelation.setRoom(roomRepository.save(room));

                userRoomRelation.setUser(userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist")));
                userRoomRelation.setUserStatus(UserStatus.ROOMJOINED);
                userRoomRelation.setQuestionsCreated(new ArrayList<>());
                userRoomRelation.setGrades(new ArrayList<>());
                userRoomRelation.setAverageGrade(0.00);
                userRoomRelation.setLiked(false);
                return userRoomRelationRepository.save(userRoomRelation);
        }


        public UserRoomRelation userCreateRoom(Room room, String userEmail) throws Exception {
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
                UserRoomRelation userRoomRelation = new UserRoomRelation();
                userRoomRelation.setId(room.getKey() + "" + user.getId());
                userRoomRelation.setRoom(room);
                userRoomRelation.setUser(user);
                userRoomRelation.setUserStatus(UserStatus.OWNER);
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public UserRoomRelation banUser(String roomKey, String userEmail) throws Exception {
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(roomKey + "" + user.getId()).orElse( new UserRoomRelation(
                        roomKey + "" + user.getId(),
                        roomRepository.findById(roomKey).orElseThrow(() -> new Exception("Room doesn't exist")),
                        userRepository.findByEmail(userEmail).orElseThrow(() -> new Exception("User doesn't exist")),
                        UserStatus.BANNED,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        0.00,
                        false
                ));
                userRoomRelation.setUserStatus(UserStatus.BANNED);
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public UserRoomRelation unbanUser(String roomKey, String userEmail) throws Exception {
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(roomKey + "" + user.getId()).orElseThrow(() -> new Exception("user is not part of room or user/room doesnt exist"));
                userRoomRelation.setUserStatus(UserStatus.CANTAKEQUIZ);
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public void leaveRoom(Integer roomKey, String userEmail) {
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
                userRoomRelationRepository.deleteById(roomKey + "" + user.getId());
        }

        public UserRoomRelation userCreateQuestion(String roomKey, String userEmail, Question newQuestion) throws Exception {
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(roomKey + "" + user.getId()).orElse(userJoinRoom(roomKey, userEmail));
                List<Question> questions = userRoomRelation.getQuestionsCreated();
                questions.add(newQuestion);
                userRoomRelation.setQuestionsCreated(questions);
                //checking if user has created all required questions
                if(userRoomRelation.getQuestionsCreated().size() >= userRoomRelation.getRoom().getQuestionsRequiredPerUser()){
                        userRoomRelation.setUserStatus(UserStatus.CANTAKEQUIZ);
                }
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public UserRoomRelation getUserRoomInfo(String roomKey, String userEmail) throws Exception {
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
                Optional<UserRoomRelation> userRoomRelation =  userRoomRelationRepository.findById(roomKey + "" + user.getId());
                if(!userRoomRelation.isPresent()) {
                        return null;
                }
                return userRoomRelation.get();
        }

        public Grade gradeQuiz(String roomKey, String userEmail, List<SubmittedQuestion> submittedQuestions) throws Exception {
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
                Grade grade = new Grade();
                List<GradedQuestion> gradedQuestions = new ArrayList<>();
                int correctQuestions = 0;
                //Creating a list of gradedQuestions
                for(int i=0; i<submittedQuestions.size(); i++){
                        GradedQuestion gradedQuestion = new GradedQuestion();
                        Question question = questionRepository.getReferenceById(submittedQuestions.get(i).getQuestionId());
                        gradedQuestion.setSelectedAnswer(submittedQuestions.get(i).getSelectedAnswer());
                        gradedQuestion.setCorrectAnswer(question.getCorrectAnswer());
                        gradedQuestion.setAnswers(question.getAnswers());
                        gradedQuestion.setQuestion(question.getQuestion());
                        gradedQuestion.setExplanation(question.getExplanation());
                        question.setAmountTimesAnswered(question.getAmountTimesAnswered()+1);
                        if(gradedQuestion.getCorrectAnswer().equals(gradedQuestion.getSelectedAnswer())){
                                question.setAmountTimesAnsweredCorrect(question.getAmountTimesAnsweredCorrect()+1);
                                gradedQuestion.setCorrect(true);
                                correctQuestions++;
                        } else {
                                gradedQuestion.setCorrect(false);
                        }
                        gradedQuestions.add(gradedQuestionRepository.save(gradedQuestion));
                        questionRepository.save(question);
                }

                grade.setGradedQuestions(gradedQuestions);
                grade.setPercentage(Math.round( ((double)correctQuestions/gradedQuestions.size() * 100.00) / 100 * 100));


                //Getting date and time of submission
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy (HH:mm)");
                String strDate = simpleDateFormat.format(date);
                grade.setSubmissionTime(strDate);

                //Adding new Grade to list of grades in userRoomRelation
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(roomKey + user.getId()).orElseThrow(() -> new Exception("user is not part of this room or user/room doesn't exist"));
                List<Grade> grades = userRoomRelation.getGrades();
                grades.add(gradeRepository.save(grade));
                userRoomRelation.setGrades(grades);

                //recalculating Average Grade to set in userRoomRelation
                double total = 0.0;
                for(int i=0; i<userRoomRelation.getGrades().size(); i++){
                       total += userRoomRelation.getGrades().get(i).getPercentage();
                }
                userRoomRelation.setAverageGrade(total / userRoomRelation.getGrades().size());
                if(userRoomRelation.getUserStatus() != UserStatus.OWNER) userRoomRelation.setUserStatus(UserStatus.QUIZTAKEN);
                return userRoomRelationRepository.save(userRoomRelation).getGrades().get(userRoomRelationRepository.getReferenceById(userRoomRelation.getId()).getGrades().size()-1);
        }

        public UserRoomStatisticsDTO getUserRoomStatistics(String roomKey) throws Exception {
                Room room = roomRepository.findById(roomKey).orElseThrow(() -> new Exception("Room doesnt exist"));
                List<UserRoomRelation> userRoomRelations = userRoomRelationRepository.findAllByRoom(room);
                List<Question> questions = questionRepository.findAllByRoom(room);
                //Calculating Average Grade
                double total = 0.0;
                int numOfGrades = 0;
                for(int i=0; i<userRoomRelations.size(); i++){
                        for(int j =0; j<userRoomRelations.get(i).getGrades().size(); j++){
                                total += userRoomRelations.get(i).getGrades().get(j).getPercentage();
                                numOfGrades++;
                        }
                }
                double averageGrade = total / numOfGrades;
                UserRoomStatisticsDTO userRoomStatisticsDTO = new UserRoomStatisticsDTO(userRoomRelations, questions, averageGrade);
                return userRoomStatisticsDTO;
        }

        public boolean likedRoom(String userEmail, String roomKey) throws Exception {
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(roomKey + "" + user.getId()).orElseThrow(() -> new Exception("user is not part of this room or user/room doesn't exist"));
                if(userRoomRelation.isLiked()) return false;
                userRoomRelation.setLiked(true);
                userRoomRelationRepository.save(userRoomRelation);
                return true;
        }

        public void dislikeRoom(String userEmail, String roomKey) throws Exception {
                User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(roomKey + "" + user.getId()).orElseThrow(() -> new Exception("user is not part of this room or user/room doesn't exist"));
                userRoomRelation.setLiked(false);
                userRoomRelationRepository.save(userRoomRelation);
        }


        public GradeDTO getGradeFromId(long id) {
                Grade grade =  gradeRepository.getReferenceById(id);
                GradeDTO gradeDTO = new GradeDTO(grade.getGradedQuestions(), grade.getSubmissionTime(), grade.getPercentage());
                return gradeDTO;
        }
}
