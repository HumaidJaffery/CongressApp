package com.quiz.together.entity;

import com.quiz.together.Enum.QuestionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    private Integer key;

    private String title;
    private String description;

    private boolean isPublic;
    private Integer totalNumOfQuestionsRequired;
    private Integer questionsRequiredPerUser;
    private String bgColor;
    private List<QuestionType> allowedQuestionTypes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @OneToMany(mappedBy = "room")
    private List<Question> questions;

    @OneToMany(mappedBy = "room")
    private List<Topic> topics;


}
