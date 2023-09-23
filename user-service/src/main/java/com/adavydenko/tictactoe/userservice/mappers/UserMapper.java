package com.adavydenko.tictactoe.userservice.mappers;

import com.adavydenko.tictactoe.userservice.dto.UserDTO;
import com.adavydenko.tictactoe.userservice.dto.UserRegistrationDTO;
import com.adavydenko.tictactoe.userservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "passwordHash", expression = "java(passwordEncoder.encode(userRegistrationDTO.getPassword()))")
    public abstract User toEntity(UserRegistrationDTO userRegistrationDTO);

    public abstract UserDTO toDTO(User user);
}
