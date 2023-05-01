package com.adavydenko.tictactoe.userservice.repositories;

import com.adavydenko.tictactoe.userservice.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(String id);

    User updateUser(User user);

    boolean deleteUserById(String id);
}
