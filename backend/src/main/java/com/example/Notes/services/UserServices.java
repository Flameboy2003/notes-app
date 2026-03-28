package com.example.Notes.services;

import com.example.Notes.dto.LoginRequestDto;
import com.example.Notes.dto.UserRegisterDto;
import com.example.Notes.dto.UserResponseDto;
import com.example.Notes.entity.Users;
import com.example.Notes.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServices(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserResponseDto registerUserService(UserRegisterDto userRegisterDto) {

        if (usersRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new IllegalArgumentException("Email is already taken :" + userRegisterDto.getEmail());
        }
        Users user = convertToEntity(userRegisterDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Users saved = usersRepository.save(user);

        return convertToResponseDto(saved);
    }

    public Users convertToEntity(UserRegisterDto userRegisterDto) {
        Users users = new Users();
        users.setEmail(userRegisterDto.getEmail());
        users.setPassword(userRegisterDto.getPassword());
        return users;
    }

    private UserResponseDto convertToResponseDto(Users users) {
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(users.getId());
        userResponseDto.setEmail(users.getEmail());
        return userResponseDto;
    }

    public Users loginUserService(LoginRequestDto loginRequestDto) {
        Users user = usersRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email not Found"));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }

        return user;

    }

}
