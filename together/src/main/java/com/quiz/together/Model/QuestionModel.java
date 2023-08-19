package com.quiz.together.Model;

import com.quiz.together.Enum.QuestionType;
import com.quiz.together.entity.Room;
import com.quiz.together.entity.Topic;
import com.quiz.together.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionModel {
    private QuestionType questionType;
    private Integer roomKey;
    private String question;
    private List<String> answers;
    private List<String> correctAnswer;
    private String explanation;
    private List<Topic> topics;
    private long user_id;

}
