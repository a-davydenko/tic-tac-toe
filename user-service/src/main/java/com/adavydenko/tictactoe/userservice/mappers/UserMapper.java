package com.adavydenko.tictactoe.userservice.mappers;

import com.adavydenko.tictactoe.userservice.dto.UserOutDTO;
import com.adavydenko.tictactoe.userservice.dto.UserInDTO;
import com.adavydenko.tictactoe.userservice.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public
interface UserMapper {

    User toEntity(UserInDTO userInDTO);

    UserOutDTO toDTO(User user);
}
