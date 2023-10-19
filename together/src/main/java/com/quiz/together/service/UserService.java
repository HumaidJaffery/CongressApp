package com.quiz.together.service;

import com.quiz.together.DTO.RoomDisplayDTO;
import com.quiz.together.DTO.UserInfoDTO;
import com.quiz.together.Enum.UserStatus;
import com.quiz.together.Model.AuthenticationRequest;
import com.quiz.together.Model.RegisterRequest;
import com.quiz.together.Repository.UserRepository;
import com.quiz.together.Repository.UserRoomRelationRepository;
import com.quiz.together.entity.Room;
import com.quiz.together.entity.User;
import com.quiz.together.entity.UserRoomRelation;
import com.quiz.together.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRoomRelationRepository userRoomRelationRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<String> signup(RegisterRequest registerRequest){
        System.out.println(registerRequest);
        if(registerRequest.getEmail() == "" || registerRequest.getDisplayName() == "" || registerRequest.getPassword() == ""){
            return ResponseEntity.badRequest().body("Username, email, or password cannot be null");
        }

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body("Account with that email already exists");
        }

        if(userRepository.existsByDisplayName(registerRequest.getDisplayName())){
            return ResponseEntity.badRequest().body("Account with that username already exists");
        }



        User user = User.builder()
                .displayName(registerRequest.getDisplayName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(jwtService.generateToken(user));
    }

    public String login(AuthenticationRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        //if this point reached, then user has been authenticated
        UserDetails user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new Exception("Email does not exist"));
        return jwtService.generateToken(user);
    }


    public UserInfoDTO getUserInfo(String email){
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesnt exist"));
        userInfoDTO.setEmail(email);
        userInfoDTO.setDisplayName(user.getDisplayName());
        List<UserRoomRelation> userRoomRelationList = userRoomRelationRepository.findAllByUser(user);

        List<RoomDisplayDTO> roomsJoined = new ArrayList<>();
        List<RoomDisplayDTO> roomsWithQuizTaken = new ArrayList<>();
        List<RoomDisplayDTO> roomsEligibleToTakeQuiz = new ArrayList<>();
        List<RoomDisplayDTO> roomsCreated = new ArrayList<>();

        for(int i=0; i<userRoomRelationList.size(); i++){
            Room room = userRoomRelationList.get(i).getRoom();
            RoomDisplayDTO roomDisplayDTO = new RoomDisplayDTO(room.getTitle(), room.getDescription(), room.getBgColor(), room.getTextColor());

            if(userRoomRelationList.get(i).getUserStatus() == UserStatus.OWNER){
                roomsCreated.add(roomDisplayDTO);
            } else if(userRoomRelationList.get(i).getUserStatus() == UserStatus.QUIZTAKEN){
                roomsWithQuizTaken.add(roomDisplayDTO);
            } else if(userRoomRelationList.get(i).getUserStatus() == UserStatus.CANTAKEQUIZ){
                roomsEligibleToTakeQuiz.add(roomDisplayDTO);
            } else {
                roomsJoined.add(roomDisplayDTO);
            }
        }

        userInfoDTO.setRoomsJoined(roomsJoined);
        userInfoDTO.setRoomsCreated(roomsCreated);
        userInfoDTO.setRoomsEligibleToTakeQuiz(roomsEligibleToTakeQuiz);
        userInfoDTO.setRoomsWithQuizTaken(roomsWithQuizTaken);
        return userInfoDTO;
    }

}
