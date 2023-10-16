package com.quiz.together.security;

import com.quiz.together.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        //bearer token
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        final String userEmail;

        //if no jwt token
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        //jwt token without "bearer"
        jwt = authHeader.substring(7);

        userEmail = jwtService.extractUsername(jwt);


        //if userEmail isn't null or already authenticated
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            System.out.println("IN JWT----------------------------------------------------------------------------------- valid token");
            //get userDetails
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //if jwtToken is valid
            if(jwtService.isTokenValid(jwt, userDetails)){
                //create auth token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                //adding extra details from request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //update security context holder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        //going to next filter
        filterChain.doFilter(request, response);
    }
}
