package com.quiz.together.controller;

import com.quiz.together.Model.RoomModel;
import com.quiz.together.entity.Room;
import com.quiz.together.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("room/")
@CrossOrigin
public class RoomController {

    @Autowired
    public RoomService roomService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/create_room")
    public ResponseEntity<Room> createRoom(@RequestBody RoomModel roomModel){
        return ResponseEntity.ok(roomService.createRoom(roomModel));
    }

    @DeleteMapping("/delete_room/{roomKey}")
    public void deleteRoom(@PathVariable Integer roomKey){
        roomService.deleteRoom(roomKey);
    }

    @PutMapping("/update_room/{roomKey}")
    public ResponseEntity<Room> updateRoom(@RequestBody RoomModel roomModel, @PathVariable Integer roomKey){
        return ResponseEntity.ok(roomService.updateRoom(roomKey, roomModel));
    }


}
