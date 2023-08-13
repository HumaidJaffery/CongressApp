package com.quiz.together.entity;

import com.quiz.together.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_room")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoomRelation {
    @Id
    @GeneratedValue
    private long id;


    @ManyToOne
    @JoinColumn(name = "room_key")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private UserStatus userStatus;

    private boolean hasTakenQuiz;

    private Integer grade;

}
