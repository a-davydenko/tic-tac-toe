package com.adavydenko.tictactoe.userservice.services;

import com.adavydenko.tictactoe.userservice.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(User user);

    List<User> findAll();

    User findById(UUID userId);

    User findByUsername(String username);

    User updateUser(UUID userId, User newUser);

    void deleteById(UUID userId);
}
