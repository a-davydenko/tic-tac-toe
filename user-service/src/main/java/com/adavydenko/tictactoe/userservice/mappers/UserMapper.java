package com.adavydenko.tictactoe.userservice.mappers;

import com.adavydenko.tictactoe.userservice.dto.UserDTO;
import com.adavydenko.tictactoe.userservice.dto.UserRegistrationDTO;
import com.adavydenko.tictactoe.userservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "passwordHash", expression = "java(userRegistrationDTO.getPassword())")
    public abstract User toEntity(UserRegistrationDTO userRegistrationDTO);

    public abstract UserDTO toDTO(User user);
}
