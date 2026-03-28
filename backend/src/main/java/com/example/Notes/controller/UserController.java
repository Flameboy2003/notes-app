package com.example.Notes.controller;

import com.example.Notes.config.JwtUtil;
import com.example.Notes.dto.LoginRequestDto;
import com.example.Notes.dto.LoginResponseDto;
import com.example.Notes.dto.UserRegisterDto;
import com.example.Notes.dto.UserResponseDto;
import com.example.Notes.entity.Users;
import com.example.Notes.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UserController {


    private final UserServices userServices;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserServices userServices, JwtUtil jwtUtil) {
        this.userServices = userServices;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        UserResponseDto response = userServices.registerUserService(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequestDto loginRequestDto) {
        Users user = userServices.loginUserService(loginRequestDto);
        String token = jwtUtil.generateToken(user);

        LoginResponseDto response = new LoginResponseDto(
                token, "Login Successful"
        );
        return ResponseEntity.ok(response);
    }

}
