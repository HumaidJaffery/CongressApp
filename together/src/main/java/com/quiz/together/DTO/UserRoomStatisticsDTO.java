package com.quiz.together.DTO;

import com.quiz.together.entity.Question;
import com.quiz.together.entity.UserRoomRelation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoomStatisticsDTO {
    private List<UserRoomRelation> userRoomRelations;
    private List<Question> questions;
    private double averageGrade;
}
