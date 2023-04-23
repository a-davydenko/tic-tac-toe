package com.adavydenko.tictactoe.userservice.services;

import com.adavydenko.tictactoe.userservice.dto.UserDTO;
import com.adavydenko.tictactoe.userservice.dto.UserRegistrationDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserRegistrationDTO userRegistrationDTO);

    List<UserDTO> getUsers();

    UserDTO getUserById(String userId);

    UserDTO updateUserById(UserDTO newUserDTO);

    boolean deleteUserById(String userId);
}
