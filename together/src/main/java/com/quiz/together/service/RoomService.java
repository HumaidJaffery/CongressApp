package com.quiz.together.service;

import com.quiz.together.Model.RoomModel;
import com.quiz.together.Model.TopicModel;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.entity.Room;
import com.quiz.together.entity.Topic;
import com.quiz.together.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String roomKey = "";
        Random random = new Random();

        while(roomKey == "" || roomRepository.existsById(roomKey)){
            roomKey = "";
            for(int i=0; i<6; i++){
                roomKey += Integer.toString(random.nextInt(10));
            }
        }

        Room room = new Room();
        room.setKey(roomKey);
        room.setChat(chatService.createChat());
        room.setBgColor(roomModel.getBgColor());
        room.setTitle(roomModel.getTitle());
        room.setDescription(roomModel.getDescription());
        room.setPublic(roomModel.isPublic());
        room.setAllowedQuestionTypes(roomModel.getAllowedQuestionTypes());
        room.setQuestionsRequiredPerUser(roomModel.getQuestionsRequiredPerUser());
        room.setTextColor(roomModel.getTextColor());
        room.setOwner(null);

        List<Topic> topics = new ArrayList<>();
        for(int i=0; i<roomModel.getTopics().size(); i++){
            TopicModel topicModel = new TopicModel(roomModel.getTopics().get(i));
            topics.add(topicService.addTopic(topicModel));
        }
        room.setTopics(topics);

        //Getting user from SecurityContextHolder
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        room.setOwner((User) user.getPrincipal());

        Room savedRoom = roomRepository.save(room);

        //creating UserRoomRelation
        userRoomService.userCreateRoom(savedRoom.getKey(), savedRoom.getOwner().getId());

        return savedRoom;
    }

    public Room updateRoom(String roomKey, RoomModel roomModel){
        Room room = roomRepository.getReferenceById(roomKey);
        room.setBgColor(roomModel.getBgColor());
        room.setTitle(roomModel.getTitle());
        room.setDescription(roomModel.getDescription());
        room.setPublic(roomModel.isPublic());
        room.setAllowedQuestionTypes(roomModel.getAllowedQuestionTypes());
        room.setQuestionsRequiredPerUser(roomModel.getQuestionsRequiredPerUser());
        room.setTextColor(roomModel.getTextColor());
        return roomRepository.save(room);
    }

    public void deleteRoom(String roomKey){
        roomRepository.deleteById(roomKey);
    }

    public Room getRoom(String roomKey){
        return roomRepository.findById(roomKey).orElseThrow(() ->  new Error("Room with key " +  roomKey + " does not exist"));
    }

    public Page<Room> getPublicRooms(int page){
        PageRequest pr = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "participantCount"));
        return roomRepository.findByIsPublic(true, pr);
    }

}
