package com.quiz.together.controller;

import com.quiz.together.DTO.GradeDTO;
import com.quiz.together.DTO.UserRoomStatisticsDTO;
import com.quiz.together.Model.SubmittedQuestion;
import com.quiz.together.entity.Grade;
import com.quiz.together.entity.User;
import com.quiz.together.entity.UserRoomRelation;
import com.quiz.together.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/user-room")
public class UserRoomController {

    @Autowired
    public UserRoomService userRoomService;

    @PostMapping("/join/{roomKey}")
    public UserRoomRelation joinRoom(@PathVariable String roomKey){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRoomService.userJoinRoom(roomKey, user.getEmail());
    }

    @PostMapping("/ban/{roomKey}")
    public UserRoomRelation banUser(@PathVariable String roomKey) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRoomService.banUser(roomKey, user.getEmail());
    }

    @PostMapping("/unban/{roomKey}")
    public UserRoomRelation unbanUser(@PathVariable String roomKey) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRoomService.unbanUser(roomKey, user.getEmail());
    }

    @PostMapping("/gradeQuiz/{roomKey}")
    public ResponseEntity<Grade> setQuizGrade(@PathVariable String roomKey, @RequestBody List<SubmittedQuestion> submittedQuestions) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userRoomService.gradeQuiz(roomKey, user.getEmail(), submittedQuestions));
    }

    @DeleteMapping("/leaveRoom/{roomKey}")
    public void leaveRoom(@PathVariable Integer roomKey) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userRoomService.leaveRoom(roomKey, user.getEmail());
    }

    @GetMapping("/getUserRoomInfo/{roomKey}")
    public ResponseEntity<UserRoomRelation> getUserStatus(@PathVariable String roomKey) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userRoomService.getUserRoomInfo(roomKey, user.getEmail()));
    }

    @GetMapping("/getUserRoomStatistics/{roomKey}")
    public ResponseEntity<UserRoomStatisticsDTO> getUserRoomStatistics(@PathVariable String roomKey) throws Exception {
        return ResponseEntity.ok(userRoomService.getUserRoomStatistics(roomKey));
    }

    @GetMapping("/getGradeFromId/{id}")
    public ResponseEntity<GradeDTO> getGradeFromId(@PathVariable long id){
        System.out.println("IN GRADE ID CONTROLLER");
        return ResponseEntity.ok().body(userRoomService.getGradeFromId(id));
    }


}
