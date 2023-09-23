package com.adavydenko.tictactoe.userservice.security;

import com.adavydenko.tictactoe.userservice.entities.User;
import com.adavydenko.tictactoe.userservice.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDB = userRepository.findByUsername(username);
        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(
                        userFromDB.getUsername(), userFromDB.getPasswordHash(), new ArrayList<>());

        return user;
    }

}
