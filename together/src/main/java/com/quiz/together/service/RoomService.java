package com.quiz.together.service;

import com.quiz.together.Model.RoomModel;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.entity.Room;
import com.quiz.together.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RoomService {

    @Autowired
    UserRoomService userRoomService;

    @Autowired
    TopicService topicService;

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
        room.setPublic(roomModel.isPublic());
        room.setAllowedQuestionTypes(roomModel.getAllowedQuestionTypes());
        room.setQuestionsRequiredPerUser(roomModel.getQuestionsRequiredPerUser());
        room.setTotalNumOfQuestionsRequired(roomModel.getTotalNumOfQuestionsRequired());

        List<Topic> topics = new ArrayList<>();
        for(int i=0; i<roomModel.getTopics().size(); i++){
            topics.add(topicService.addTopic(roomModel.getTopics().get(i)));
        }
        room.setTopics(topics);

        //creating UserRoomRelation
        userRoomService.userCreateRoom(roomKey, roomModel.getUserId());

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
