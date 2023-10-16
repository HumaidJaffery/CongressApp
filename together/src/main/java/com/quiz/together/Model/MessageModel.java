package com.quiz.together.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageModel {
    private String text;
    private Date date;
    private String userEmail;
    private long chatId;

}
