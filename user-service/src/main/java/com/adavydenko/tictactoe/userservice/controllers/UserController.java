package com.adavydenko.tictactoe.userservice.controllers;

import com.adavydenko.tictactoe.userservice.dto.UserOutDTO;
import com.adavydenko.tictactoe.userservice.dto.UserInDTO;
import com.adavydenko.tictactoe.userservice.entities.User;
import com.adavydenko.tictactoe.userservice.mappers.UserMapper;
import com.adavydenko.tictactoe.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutDTO register(@RequestBody UserInDTO userRegistrationDTO) {
        User user = userMapper.toEntity(userRegistrationDTO);
        userService.createUser(user);

        return userMapper.toDTO(user);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserOutDTO> getAllUsers() {
        List<User> users = userService.findAll();

        return users.stream().map(user -> userMapper.toDTO(user)).toList();
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutDTO getUserByUserId(@PathVariable UUID userId) {
        User user = userService.findById(userId);

        return userMapper.toDTO(user);
    }

    @PutMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutDTO updateUser(@PathVariable UUID userId, @RequestBody UserInDTO userDTO, Authentication authentication) throws Exception {
        currentUserValidation(userId, authentication);

        User user = userMapper.toEntity(userDTO);
        User updatedUser = userService.updateUser(userId, user);

        return userMapper.toDTO(updatedUser);
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteUserByUserId(@PathVariable UUID userId, Authentication authentication) throws Exception {
        currentUserValidation(userId, authentication);
        userService.deleteById(userId);

        return true;
    }

    private void currentUserValidation(UUID userId, Authentication authentication) throws Exception {
        Jwt principal = (Jwt) authentication.getPrincipal();
        Map<String, Object> claims = principal.getClaims();
        String email = (String) claims.get("email");

        if (StringUtils.isEmpty(email)) {
            throw new Exception("Authenticated user email is null");
        }

        User userFromDb = userService.findById(userId);

        if (userFromDb == null) {
            throw new Exception("User not found");
        } else if (!StringUtils.equals(userFromDb.getEmail(), email)) {
            throw new Exception("Not allowed");
        }
    }
}
