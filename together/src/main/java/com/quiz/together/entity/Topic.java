package com.quiz.together.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String name;

    @ManyToMany
    @JoinTable(name = "topic_questions", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    @JsonBackReference
    private List<Question> questions;

}
