package com.adavydenko.tictactoe.userservice.services;

import com.adavydenko.tictactoe.userservice.entities.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> findAll();

    User findById(Long userId);

    User findByUsername(String username);

    User updateUser(Long userId, User newUser);

    void deleteById(Long userId);
}
