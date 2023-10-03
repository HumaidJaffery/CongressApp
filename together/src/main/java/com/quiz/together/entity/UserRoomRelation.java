package com.quiz.together.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.quiz.together.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
    private String id;

    @ManyToOne
    @JoinColumn(name = "room_key")
    @JsonIgnore
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private boolean hasTakenQuiz;

    @OneToMany
    @JoinColumn
    @JsonManagedReference
    private List<Question> questionsCreated;

    @OneToOne
    @JoinColumn
    @JsonManagedReference
    private Grade grade;
}
