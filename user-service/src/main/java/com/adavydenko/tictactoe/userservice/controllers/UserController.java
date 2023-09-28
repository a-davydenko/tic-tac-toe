package com.adavydenko.tictactoe.userservice.controllers;

import com.adavydenko.tictactoe.userservice.dto.UserDTO;
import com.adavydenko.tictactoe.userservice.dto.UserRegistrationDTO;
import com.adavydenko.tictactoe.userservice.entities.User;
import com.adavydenko.tictactoe.userservice.mappers.UserMapper;
import com.adavydenko.tictactoe.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.findAll();

        return users.stream().map(user -> userMapper.toDTO(user)).toList();
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByUserId(@PathVariable UUID userId) {
        User user = userService.findById(userId);

        return userMapper.toDTO(user);
    }

    @PutMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable UUID userId, @RequestBody UserRegistrationDTO userDTO, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        currentUserValidation(userId, userDetails);
        User user = userMapper.toEntity(userDTO);
        User updatedUser = userService.updateUser(userId, user);

        return userMapper.toDTO(updatedUser);
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteUserByUserId(@PathVariable UUID userId, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        currentUserValidation(userId, userDetails);
        userService.deleteById(userId);

        return true;
    }

    private void currentUserValidation(UUID userId, UserDetails userDetails) throws Exception {
        User userFromDb = userService.findById(userId);

        if (userFromDb == null) {
            throw new Exception("User not found");
        } else if (!StringUtils.equals(userFromDb.getUsername(), userDetails.getUsername())) {
            throw new Exception("Not allowed");
        }
    }
}
