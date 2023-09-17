package com.quiz.together.controller;

import com.quiz.together.entity.Question;
import com.quiz.together.entity.UserRoomRelation;
import com.quiz.together.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/user-room")
public class UserRoomController {

    @Autowired
    public UserRoomService userRoomService;

    @PostMapping("/join/{roomKey}/{userId}")
    public UserRoomRelation joinRoom(@PathVariable String roomKey, @PathVariable long userId){
        return userRoomService.userJoinRoom(roomKey, userId);
    }

    @PostMapping("/ban/{roomKey}/{userId}")
    public UserRoomRelation banUser(@PathVariable String roomKey, @PathVariable long userId) throws Exception {
        return userRoomService.BanUser(roomKey, userId);
    }

    @PostMapping("/unban/{roomKey}/{userId}")
    public UserRoomRelation unbanUser(@PathVariable String roomKey, @PathVariable long userId) throws Exception {
        return userRoomService.UnbanUser(roomKey, userId);
    }

//    @PostMapping("/setQuizGrade/{roomKey}/{userId}")
//    public UserRoomRelation setQuizGrade(@PathVariable Integer roomKey, @PathVariable long userId, @RequestBody Pair<Integer, Integer> grade) throws Exception {
//        return userRoomService.setQuizGrade(roomKey, userId, grade);
//    }

    @DeleteMapping("/leave_room/{roomKey}/{userId}")
    public void leaveRoom(@PathVariable Integer roomKey, @PathVariable long userId) {
        userRoomService.leaveRoom(roomKey, userId);
    }


}
