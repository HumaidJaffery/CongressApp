package com.quiz.together.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.quiz.together.Enum.QuestionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    private String key;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    private String title;
    private String description;

    private boolean isPublic;
    private Integer questionsRequiredPerUser;
    private String bgColor;
    private String textColor;
    private List<QuestionType> allowedQuestionTypes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    @JsonIgnore
    private List<Question> questions;

    @OneToMany
    @JsonManagedReference
    private List<Topic> topics;

    private int participantCount;

    private int likes;


}
