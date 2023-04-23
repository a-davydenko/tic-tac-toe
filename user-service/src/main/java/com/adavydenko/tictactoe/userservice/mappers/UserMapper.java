package com.adavydenko.tictactoe.userservice.mappers;

import com.adavydenko.tictactoe.userservice.dto.UserDTO;
import com.adavydenko.tictactoe.userservice.dto.UserRegistrationDTO;
import com.adavydenko.tictactoe.userservice.entities.User;

public interface UserMapper {
    User toEntity(UserRegistrationDTO userRegistrationDTO);

    UserDTO toDTO(User user);
}
