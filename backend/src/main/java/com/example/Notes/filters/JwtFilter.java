package com.example.Notes.filters;


import com.example.Notes.config.JwtUtil;
import com.example.Notes.entity.Users;
import com.example.Notes.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UsersRepository usersRepository) {
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//        String email = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            email = jwtUtil.extractUserEmail(token);
//        }
//
//        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            Users user = usersRepository.findByEmail(email).get();
//            if (jwtUtil.validateToken(token)) {
//                Authentication auth =
//                        new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//
//        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtil.extractUserEmail(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                new ArrayList<>()
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
//
//        System.out.println("Authorization header: " + authHeader);
//        System.out.println("Extracted token: " + token);
//        System.out.println("Extracted email: " + email);
//        System.out.println("validate = " + jwtUtil.validateToken(token));


    }
}
