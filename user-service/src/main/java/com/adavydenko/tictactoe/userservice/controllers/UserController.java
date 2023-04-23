package com.adavydenko.tictactoe.userservice.controllers;

import com.adavydenko.tictactoe.userservice.dto.UserDTO;
import com.adavydenko.tictactoe.userservice.dto.UserRegistrationDTO;
import com.adavydenko.tictactoe.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserDTO savedUser = userService.createUser(userRegistrationDTO);
        HttpStatus status = savedUser == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED;

        return new ResponseEntity<>(savedUser, status);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserById(@RequestParam String userId) {
        UserDTO userDTO = userService.getUserById(userId);

        return userDTO == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<UserDTO> updateUserById(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUserById(userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<HttpStatus> deleteUserById(@RequestBody String userId) {
        HttpStatus status = userService.deleteUserById(userId) ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(status);
    }
}
