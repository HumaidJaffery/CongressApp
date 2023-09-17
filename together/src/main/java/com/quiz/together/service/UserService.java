package com.quiz.together.service;

import com.quiz.together.Model.AuthenticationRequest;
import com.quiz.together.Model.RegisterRequest;
import com.quiz.together.Repository.UserRepository;
import com.quiz.together.entity.User;
import com.quiz.together.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<String> signup(RegisterRequest registerRequest){
        System.out.println(registerRequest);
        if(registerRequest.getEmail() == null || registerRequest.getUsername() == null || registerRequest.getPassword() == null){
            return ResponseEntity.badRequest().body("Username, email, and password cannot be null");
        }

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body("Account with that email already exists");
        }

        if(userRepository.existsByUsername(registerRequest.getUsername())){
            return ResponseEntity.badRequest().body("Account with that username already exists");
        }



        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(jwtService.generateToken(user));
    }

    public String login(AuthenticationRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        //if this point reached, then user has been authenticated
        UserDetails user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new Exception("Email does not exist"));
        return jwtService.generateToken(user);


    }

}
