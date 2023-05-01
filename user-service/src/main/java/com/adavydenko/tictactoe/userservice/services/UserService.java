package com.adavydenko.tictactoe.userservice.services;

import com.adavydenko.tictactoe.userservice.entities.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getUsers();

    User getUserById(String userId);

    User updateUserById(User newUser);

    boolean deleteUserById(String userId);
}
