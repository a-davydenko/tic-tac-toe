package com.adavydenko.tictactoe.battleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateBattleDTO {
    private UUID userId;
    private int gridSize;
    private int winSize;
}
