package com.adavydenko.tictactoe.userservice.services;

import com.adavydenko.tictactoe.userservice.entities.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> findAll();

    User findById(String userId);

    User updateUser(String userId, User newUser);

    void deleteById(String userId);
}
