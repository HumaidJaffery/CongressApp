package com.quiz.together.service;

import com.quiz.together.Model.AuthenticationRequest;
import com.quiz.together.Model.AuthenticationResponse;
import com.quiz.together.Model.RegisterRequest;
import com.quiz.together.Repository.UserRepository;
import com.quiz.together.entity.User;
import com.quiz.together.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthenticationResponse register(RegisterRequest registerRequest){
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().jwtToken(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        //if this point reached, then user has been authenticated
        User user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().jwtToken(jwtToken).build();

    }


}
