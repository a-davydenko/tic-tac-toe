package com.adavydenko.tictactoe.userservice.services.impl;

import com.adavydenko.tictactoe.userservice.entities.User;
import com.adavydenko.tictactoe.userservice.repositories.UserRepository;
import com.adavydenko.tictactoe.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public User findById(String userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User updateUser(String userId, User newUser) {
        User oldUser = userRepository.findById(userId).get();

        if (oldUser != null) {
            oldUser.merge(newUser);

            User user = userRepository.save(oldUser);
            return user;
        }

        return null;
    }

    @Override
    public void deleteById(String userId) {
        userRepository.deleteById(userId);
    }
}
