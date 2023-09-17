package com.quiz.together.Model;

import com.quiz.together.Enum.QuestionType;
import com.quiz.together.entity.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HexFormat;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomModel {
    private String title;
    private String description;
    private boolean isPublic;

    private Integer questionsRequiredPerUser;
    private String bgColor;
    private String textColor;

    private List<String> topics;
    private List<QuestionType> allowedQuestionTypes;
}
