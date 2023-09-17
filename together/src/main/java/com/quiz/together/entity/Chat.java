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

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;
}
