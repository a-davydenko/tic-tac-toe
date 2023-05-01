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
        List<User> users = userService.getUsers();

        return users.stream().map(user -> userMapper.toDTO(user)).toList();
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@RequestParam String userId) {
        User user = userService.getUserById(userId);

        return userMapper.toDTO(user);
    }

    @PutMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUserById(@RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User updatedUser = userService.updateUserById(user);

        return userMapper.toDTO(updatedUser);
    }

    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteUserById(@RequestBody String userId) {
        boolean success = userService.deleteUserById(userId);

        return success;
    }
}
