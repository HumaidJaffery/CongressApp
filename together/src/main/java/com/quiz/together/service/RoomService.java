package com.quiz.together.service;

import com.quiz.together.Model.RoomModel;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RoomService {

    @Autowired
    ChatService chatService;

    @Autowired
    RoomRepository roomRepository;

    public Room createRoom(RoomModel roomModel){
        String tmp = "";
        Random random = new Random();
        for(int i=0; i<5; i++){
            tmp += Integer.toString(random.nextInt(10));
        }
        Integer roomKey = Integer.parseInt(tmp);

        Room room = new Room();
        room.setKey(roomKey);
        room.setChat(chatService.createChat(roomKey));
        room.setBgColor(roomModel.getBgColor());
        room.setTitle(roomModel.getTitle());
        room.setDescription(roomModel.getDescription());
        room.setTopics(roomModel.getTopics());
        room.setPublic(roomModel.isPublic());
        room.setAllowedQuestionTypes(roomModel.getAllowedQuestionTypes());
        room.setQuestionsRequiredPerUser(roomModel.getQuestionsRequiredPerUser());
        room.setTotalNumOfQuestionsRequired(roomModel.getTotalNumOfQuestionsRequired());
        return roomRepository.save(room);
    }

    public Room updateRoom(Integer roomKey, RoomModel roomModel){
        Room room = roomRepository.getReferenceById(roomKey);
        room.setBgColor(roomModel.getBgColor());
        room.setTitle(roomModel.getTitle());
        room.setDescription(roomModel.getDescription());
        room.setPublic(roomModel.isPublic());
        room.setAllowedQuestionTypes(roomModel.getAllowedQuestionTypes());
        room.setQuestionsRequiredPerUser(roomModel.getQuestionsRequiredPerUser());
        room.setTotalNumOfQuestionsRequired(roomModel.getTotalNumOfQuestionsRequired());
        return roomRepository.save(room);
    }

    public void deleteRoom(Integer roomKey){
        roomRepository.deleteById(roomKey);
    }

}
