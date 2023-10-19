package com.quiz.together.controller;

import com.quiz.together.Model.RoomModel;
import com.quiz.together.entity.Room;
import com.quiz.together.entity.User;
import com.quiz.together.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomController {

    @Autowired
    public RoomService roomService;


    @PostMapping("/add")
    public ResponseEntity<Room> createRoom(@RequestBody RoomModel roomModel) throws Exception {
        return ResponseEntity.ok(roomService.createRoom(roomModel));
    }

    @DeleteMapping("/delete/{roomKey}")
    public void deleteRoom(@PathVariable String roomKey){
        roomService.deleteRoom(roomKey);
    }

    @PutMapping("/update/{roomKey}")
    public ResponseEntity<Room> updateRoom(@RequestBody RoomModel roomModel, @PathVariable String roomKey){
        return ResponseEntity.ok(roomService.updateRoom(roomKey, roomModel));
    }

    @GetMapping("/get/{roomKey}")
    public Room getRoom(@PathVariable String roomKey){
            return roomService.getRoom(roomKey);
    }

    @GetMapping("/getPublic/{page}")
    public Page<Room> getPublicRooms(@PathVariable int page){
        return roomService.getPublicRooms(page);
    }

    @GetMapping("/getTotalNumberOfQuestions/{roomKey}")
    public Integer getTotalNumberOfQuestions(@PathVariable String roomKey){
        return roomService.getTotalNumberOfQuestions(roomKey);
    }

    @GetMapping("/search/{keyword}/{page}")
    public Page<Room> getBySearch(@PathVariable String keyword, @PathVariable int page){
        return roomService.findBySearch(page, keyword);
    }

    @PostMapping("/like/{roomKey}")
    public void like(@PathVariable String roomKey) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        roomService.likeRoom(user.getEmail(), roomKey);
    }

    @PostMapping("/unlike/{roomKey}")
    public void unlike(@PathVariable String roomKey) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        roomService.unlikeRoom(user.getEmail(), roomKey);
    }


}
