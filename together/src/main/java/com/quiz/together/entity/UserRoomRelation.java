package com.quiz.together.entity;

import com.quiz.together.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.util.Pair;

import java.util.List;

@Entity
@Table(name = "user_room")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRoomRelation {

    //will made up of roomKey + "" + userId
    @Id
    private long id;


    @ManyToOne
    @JoinColumn(name = "room_key")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private UserStatus userStatus;

    private boolean hasTakenQuiz;

    private Pair<Integer, Integer> grade;

    @OneToMany
    @JoinColumn(name = "questions")
    private List<Question> questions;

}
