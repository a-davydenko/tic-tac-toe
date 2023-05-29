package com.adavydenko.tictactoe.battleservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BattleStatus {
    NEW,
    IN_PROGRESS,
    FINISHED;
}
