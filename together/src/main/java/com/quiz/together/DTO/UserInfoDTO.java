package com.quiz.together.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoDTO {
    private String displayName;
    private String email;
    private List<RoomDisplayDTO> roomsJoined;
    private List<RoomDisplayDTO> roomsCreated;
    private List<RoomDisplayDTO> roomsWithQuizTaken;
    private List<RoomDisplayDTO> roomsEligibleToTakeQuiz;
}
