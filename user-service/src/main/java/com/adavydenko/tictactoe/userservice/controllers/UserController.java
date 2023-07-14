package com.adavydenko.tictactoe.userservice.controllers;

import com.adavydenko.tictactoe.userservice.dto.UserDTO;
import com.adavydenko.tictactoe.userservice.entities.User;
import com.adavydenko.tictactoe.userservice.mappers.UserMapper;
import com.adavydenko.tictactoe.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public UserDTO getUserByUserId(@PathVariable String userId) {
        User user = userService.findById(userId);

        return userMapper.toDTO(user);
    }

    @PutMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User updatedUser = userService.updateUser(userId, user);

        return userMapper.toDTO(updatedUser);
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteUserByUserId(@PathVariable String userId) {
        userService.deleteById(userId);

        return true;
    }
}
