package com.quiz.together.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Room room;

    private String name;

    @ManyToMany
    @JoinTable(name = "topic_questions", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questions;

}
