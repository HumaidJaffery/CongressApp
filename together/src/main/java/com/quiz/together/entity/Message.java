package com.quiz.together.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    @Id
    @GeneratedValue
    private long id;

    private Date date;

    private String text;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
