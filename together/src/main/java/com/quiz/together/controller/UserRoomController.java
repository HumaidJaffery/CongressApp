package com.quiz.together.controller;

import com.quiz.together.Enum.UserStatus;
import com.quiz.together.entity.User;
import com.quiz.together.entity.UserRoomRelation;
import com.quiz.together.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/user-room")
public class UserRoomController {

    @Autowired
    public UserRoomService userRoomService;

    @PostMapping("/join/{roomKey}")
    public UserRoomRelation joinRoom(@PathVariable String roomKey){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRoomService.userJoinRoom(roomKey, user.getId());
    }

    @PostMapping("/ban/{roomKey}")
    public UserRoomRelation banUser(@PathVariable String roomKey) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRoomService.banUser(roomKey, user.getId());
    }

    @PostMapping("/unban/{roomKey}")
    public UserRoomRelation unbanUser(@PathVariable String roomKey) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRoomService.unbanUser(roomKey, user.getId());
    }

//    @PostMapping("/setQuizGrade/{roomKey}/{userId}")
//    public UserRoomRelation setQuizGrade(@PathVariable Integer roomKey, @PathVariable long userId, @RequestBody Pair<Integer, Integer> grade) throws Exception {
//        return userRoomService.setQuizGrade(roomKey, userId, grade);
//    }

    @DeleteMapping("/leaveRoom/{roomKey}")
    public void leaveRoom(@PathVariable Integer roomKey) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userRoomService.leaveRoom(roomKey, user.getId());
    }

//    @GetMapping("/getUserStatus/{roomKey}")
//    public ResponseEntity<UserStatus> getUserStatus(@PathVariable String roomKey){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return ResponseEntity.ok(userRoomService.getUserStatus(roomKey, user.getId()));
//    }


}
