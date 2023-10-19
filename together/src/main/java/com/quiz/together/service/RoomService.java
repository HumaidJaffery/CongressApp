package com.quiz.together.service;

import com.quiz.together.Model.RoomModel;
import com.quiz.together.Model.TopicModel;
import com.quiz.together.Repository.RoomRepository;
import com.quiz.together.Repository.UserRepository;
import com.quiz.together.entity.Question;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private UserRepository userRepository;

    public Room createRoom(RoomModel roomModel) throws Exception {
        String roomKey = "";
        Random random = new Random();

        while(roomKey.equals("") || roomRepository.existsById(roomKey)){
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
        room.setLikes(0);
        room.setParticipantCount(0);

        List<Topic> topics = new ArrayList<>();
        for(int i=0; i<roomModel.getTopics().size(); i++){
            TopicModel topicModel = new TopicModel(roomModel.getTopics().get(i));
            topics.add(topicService.addTopic(topicModel));
        }
        room.setTopics(topics);

        //Getting user from SecurityContextHolder
        String email =  ( (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user doesnt exist"));
        room.setOwner(user);

        Room savedRoom = roomRepository.save(room);

        //creating UserRoomRelation
        userRoomService.userCreateRoom(savedRoom, savedRoom.getOwner().getEmail());

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
        PageRequest pr = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "likes"));
        return roomRepository.findByIsPublic(true, pr);
    }

    public Integer getTotalNumberOfQuestions(String roomKey){
        return roomRepository.getReferenceById(roomKey).getQuestions().size();
    }

    public Page<Room> findBySearch(int page, String keyword){
        PageRequest pr = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "likes"));
        return roomRepository.findByTitleContainsIgnoreCase(keyword, pr);
    }

    public void likeRoom(String userEmail, String roomKey) throws Exception {
        Room room = roomRepository.getReferenceById(roomKey);
        if(userRoomService.likedRoom(userEmail, roomKey)){
            room.setLikes(room.getLikes()+1);
            roomRepository.save(room);
        }
    }

    public void unlikeRoom(String userEmail, String roomKey) throws Exception {
        Room room = roomRepository.getReferenceById(roomKey);
        room.setLikes(room.getLikes()-1);
        userRoomService.dislikeRoom(userEmail, roomKey);
        roomRepository.save(room);
    }

}
