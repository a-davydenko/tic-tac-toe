package com.adavydenko.tictactoe.battleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class StepDTO {
    private UUID playerId;
    private int x;
    private int y;
}
