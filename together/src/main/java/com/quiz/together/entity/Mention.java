package com.quiz.together.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "mention")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mention {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name = "message_mention", referencedColumnName = "id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean read;


}
