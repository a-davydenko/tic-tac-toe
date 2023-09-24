package com.adavydenko.tictactoe.userservice.controllers;

import com.adavydenko.tictactoe.userservice.dto.UserDTO;
import com.adavydenko.tictactoe.userservice.dto.UserRegistrationDTO;
import com.adavydenko.tictactoe.userservice.entities.User;
import com.adavydenko.tictactoe.userservice.mappers.UserMapper;
import com.adavydenko.tictactoe.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LoginController {
    private UserService userService;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User user = userMapper.toEntity(userRegistrationDTO);
        userService.createUser(user);

        return userMapper.toDTO(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.FOUND)
    public String authenticateUser(@RequestBody UserRegistrationDTO loginDto) throws Exception {
        User userFromDb = userService.findByUsername(loginDto.getUsername());

        if (userFromDb != null && passwordEncoder.matches(loginDto.getPassword(), userFromDb.getPasswordHash())) {
            return "User signed-in successfully!";
        } else {
            throw new Exception("Wrong username or password");
        }
    }

}
