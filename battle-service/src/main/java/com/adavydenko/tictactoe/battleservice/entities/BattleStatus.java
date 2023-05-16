package com.adavydenko.tictactoe.battleservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BattleStatus {
    NEW(0),
    IN_PROGRESS(1),
    FINISHED(2);

    private final int id;
}
