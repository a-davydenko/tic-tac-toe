package com.adavydenko.tictactoe.statisticsservice.services;

import com.adavydenko.tictactoe.statisticsservice.entities.GeneralStatistics;
import com.adavydenko.tictactoe.statisticsservice.entities.PlayerStatistics;

import java.util.List;

public interface StatisticsService {

    GeneralStatistics getGeneralStatistics();

    PlayerStatistics getPlayerStatisticsByPlayerId(String playerId);

    List<PlayerStatistics> getRating();
}
