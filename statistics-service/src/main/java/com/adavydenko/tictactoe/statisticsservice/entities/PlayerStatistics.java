package com.adavydenko.tictactoe.statisticsservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.adavydenko.tictactoe.userservice.entities.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatistics extends GeneralStatistics{
    private User player;
    private int nOfWins;
    private int nOfDraw;
    private int nOfLoss;

    public PlayerStatistics(User player) {
        this.player = player;
    }

    public int addNOfWins() {
        return nOfWins++;
    }

    public int addNOfDraw() {
        return nOfDraw++;
    }

    public int addNOfLoss() {
        return nOfLoss++;
    }
}
