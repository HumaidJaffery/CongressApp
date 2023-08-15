package com.quiz.together.controller;

import com.quiz.together.Model.AuthenticationRequest;
import com.quiz.together.Model.RegisterRequest;
import com.quiz.together.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("auth/")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> register(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(userService.authenticate(authenticationRequest));
    }

}
