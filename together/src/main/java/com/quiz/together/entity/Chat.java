package com.quiz.together.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chat")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue()
    private long id;

    @OneToOne(mappedBy = "chat")
    private Room room;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    public Chat(Room room) {
        this.room = room;
    }
}
