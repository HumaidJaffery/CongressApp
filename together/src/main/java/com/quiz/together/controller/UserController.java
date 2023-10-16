package com.quiz.together.controller;

import com.quiz.together.DTO.UserInfoDTO;
import com.quiz.together.entity.User;
import com.quiz.together.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/getInfo")
    public UserInfoDTO getUserInfo() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserInfo(user.getEmail());
    }
}
