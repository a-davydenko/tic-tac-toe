package com.adavydenko.tictactoe.battleservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Coordinate {
    A(0), B(1), C(2);

    private final int number;
}
