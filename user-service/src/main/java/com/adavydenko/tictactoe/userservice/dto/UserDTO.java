package com.adavydenko.tictactoe.userservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private LocalDateTime lastSeenOnline;
}
