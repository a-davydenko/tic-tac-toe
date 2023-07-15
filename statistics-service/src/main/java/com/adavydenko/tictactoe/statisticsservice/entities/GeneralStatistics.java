package com.adavydenko.tictactoe.statisticsservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralStatistics {
    private int nOfGames;
    private Duration playTime;

    public int addGame() {
        return nOfGames++;
    }

    public Duration addPlayTime(@NonNull Duration duration) {
        if (playTime == null) {
            playTime = Duration.ZERO;
        }

        playTime = playTime.plus(duration);

        return playTime;
    }
}
