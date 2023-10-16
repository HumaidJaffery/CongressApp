package com.quiz.together.Model;

import com.quiz.together.Enum.QuestionType;
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
    private String roomKey;
    private String question;
    private List<String> answers;
    private String correctAnswer;
    private String explanation;
    private List<Long> topicIds;
}
