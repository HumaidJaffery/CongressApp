package com.quiz.together.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDisplayDTO {
    private String key;
    private String title;
    private String description;
    private String bgColor;
    private String textColor;
}
