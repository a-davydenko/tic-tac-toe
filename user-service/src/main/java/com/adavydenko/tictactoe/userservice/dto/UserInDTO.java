package com.adavydenko.tictactoe.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInDTO {
    private String username;
    private String email;
}
