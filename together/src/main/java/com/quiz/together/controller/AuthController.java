package com.quiz.together.controller;

import com.quiz.together.Model.AuthenticationRequest;
import com.quiz.together.Model.RegisterRequest;
import com.quiz.together.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        return userService.signup(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//        System.out.println(authenticationRequest);
        return ResponseEntity.ok(userService.login(authenticationRequest));
    }


}
