package com.quiz.together.service;

import com.quiz.together.Enum.UserStatus;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.Repository.UserRepository;
import com.quiz.together.Repository.UserRoomRelationRepository;
import com.quiz.together.entity.Question;
import com.quiz.together.entity.Room;
import com.quiz.together.entity.User;
import com.quiz.together.entity.UserRoomRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRoomService {

        @Autowired
        public UserRoomRelationRepository userRoomRelationRepository;

        @Autowired
        public RoomRepository roomRepository;

        @Autowired
        public UserRepository userRepository;


        public UserRoomRelation userJoinRoom(Integer roomKey, long userId){
                UserRoomRelation userRoomRelation = new UserRoomRelation();
                userRoomRelation.setId(Integer.parseInt(roomKey + "" + userId));
                userRoomRelation.setRoom(roomRepository.getReferenceById(roomKey));
                userRoomRelation.setUser(userRepository.getReferenceById(userId));
                userRoomRelation.setUserStatus(UserStatus.PARTICIPANT);
                userRoomRelation.setHasTakenQuiz(false);
                return userRoomRelationRepository.save(userRoomRelation);
        }


        public UserRoomRelation userCreateRoom(Integer roomKey, long userId){
                UserRoomRelation userRoomRelation = new UserRoomRelation();
                userRoomRelation.setId(Integer.parseInt(roomKey + "" + userId));
                userRoomRelation.setRoom(roomRepository.getReferenceById(roomKey));
                userRoomRelation.setUser(userRepository.getReferenceById(userId));
                userRoomRelation.setUserStatus(UserStatus.OWNER);
                userRoomRelation.setHasTakenQuiz(false);
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public UserRoomRelation BanUser(Integer roomKey, long userId) throws Exception {
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(Long.parseLong(roomKey + "" + userId)).orElse(new UserRoomRelation(
                        Long.parseLong(roomKey + "" + userId),
                        roomRepository.findById(roomKey).orElseThrow(() -> new Exception("Room doesn't exist")),
                        userRepository.findById(userId).orElseThrow(() -> new Exception("User doesn't exist")),
                        UserStatus.BANNED,
                        false,
                        null,
                        new ArrayList<>()
                ));
                userRoomRelation.setUserStatus(UserStatus.BANNED);
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public UserRoomRelation UnbanUser(Integer roomKey, long userId) throws Exception {
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(Long.parseLong(roomKey + "" + userId)).orElseThrow(() -> new Exception("user is not part of room or user/room doesnt exist"));
                userRoomRelation.setUserStatus(UserStatus.PARTICIPANT);
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public void leaveRoom(Integer roomKey, long userId) {
                userRoomRelationRepository.deleteById(Long.parseLong(roomKey + "" + userId));
        }

//        public UserRoomRelation UserAddQuestion(Integer roomKey, long userId, Question question) throws Exception {
//                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(Long.parseLong(roomKey + "" + userId)).orElseThrow(() -> new Exception("user is not part of this room or user/room doesn't exist"));
//                List<Question> questions = userRoomRelation.getQuestions();
//                questions.add(question);
//                userRoomRelation.setQuestions(questions);
//                return userRoomRelationRepository.save(userRoomRelation);
//        }

        public UserRoomRelation setQuizGrade(Integer roomKey, long userId, Pair<Integer, Integer> grade) throws Exception {
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(Long.parseLong(roomKey + "" + userId)).orElseThrow(() -> new Exception("user is not part of this room or user/room doesn't exist"));
                userRoomRelation.setGrade(grade);
                userRoomRelation.setHasTakenQuiz(true);
                return userRoomRelationRepository.save(userRoomRelation);
        }


}
