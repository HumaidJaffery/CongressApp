package com.quiz.together.controller;

import com.quiz.together.Model.RoomModel;
import com.quiz.together.entity.Room;
import com.quiz.together.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomController {

    @Autowired
    public RoomService roomService;


    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@RequestBody RoomModel roomModel){
        System.out.println("In Create Room controller");
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


}
