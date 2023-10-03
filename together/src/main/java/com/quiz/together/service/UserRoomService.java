package com.quiz.together.service;

import com.quiz.together.Enum.UserStatus;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.Repository.UserRepository;
import com.quiz.together.Repository.UserRoomRelationRepository;
import com.quiz.together.entity.*;
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
        private UserRoomRelationRepository userRoomRelationRepository;

        @Autowired
        private RoomRepository roomRepository;

        @Autowired
        private UserRepository userRepository;


        public UserRoomRelation userJoinRoom(String roomKey, long userId){
                UserRoomRelation userRoomRelation = new UserRoomRelation();
                userRoomRelation.setId(roomKey + "" + userId);
                userRoomRelation.setRoom(roomRepository.getReferenceById(roomKey));
                userRoomRelation.setUser(userRepository.getReferenceById(userId));
                userRoomRelation.setUserStatus(UserStatus.ROOMJOINED);
                userRoomRelation.setHasTakenQuiz(false);
                return userRoomRelationRepository.save(userRoomRelation);
        }


        public UserRoomRelation userCreateRoom(Room room, User user){
                UserRoomRelation userRoomRelation = new UserRoomRelation();
                userRoomRelation.setId(room.getKey() + "" + user.getId());
                userRoomRelation.setRoom(room);
                userRoomRelation.setUser(user);
                userRoomRelation.setUserStatus(UserStatus.OWNER);
                userRoomRelation.setHasTakenQuiz(false);
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public UserRoomRelation banUser(String roomKey, long userId) throws Exception {
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(roomKey + "" + userId).orElse(new UserRoomRelation(
                        roomKey + "" + userId,
                        roomRepository.findById(roomKey).orElseThrow(() -> new Exception("Room doesn't exist")),
                        userRepository.findById(userId).orElseThrow(() -> new Exception("User doesn't exist")),
                        UserStatus.BANNED,
                        false,
                        new ArrayList<>(),
                        null
                ));
                userRoomRelation.setUserStatus(UserStatus.BANNED);
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public UserRoomRelation unbanUser(String roomKey, long userId) throws Exception {
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(roomKey + "" + userId).orElseThrow(() -> new Exception("user is not part of room or user/room doesnt exist"));
                userRoomRelation.setUserStatus(UserStatus.QUESTIONSCREATED);
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public void leaveRoom(Integer roomKey, long userId) {
                userRoomRelationRepository.deleteById(roomKey + "" + userId);
        }

        public UserRoomRelation userCreateQuestion(String roomKey, long userId, Question newQuestion) throws Exception {
                UserRoomRelation userRoomRelation = userRoomRelationRepository.findById(roomKey + "" + userId).orElseThrow(() -> new Exception("user is not part of this room or user/room doesn't exist"));
                List<Question> questions = userRoomRelation.getQuestionsCreated();
                questions.add(newQuestion);
                userRoomRelation.setQuestionsCreated(questions);
                //checking if user has created all required questions
                if(userRoomRelation.getQuestionsCreated().size() >= userRoomRelation.getRoom().getQuestionsRequiredPerUser()){
                        userRoomRelation.setUserStatus(UserStatus.QUESTIONSCREATED);
                }
                return userRoomRelationRepository.save(userRoomRelation);
        }

        public UserRoomRelation getUserRoomInfo(String roomKey, long userId){
                Optional<UserRoomRelation> userRoomRelation =  userRoomRelationRepository.findById(roomKey + "" + userId);
                if(!userRoomRelation.isPresent()) throw new Error("User/Room doesn't exist");
                return userRoomRelation.get();
        }

        public Grade gradeQuestions(List<Question> questions, long userId, String roomKey){
                return null;
        }


}
