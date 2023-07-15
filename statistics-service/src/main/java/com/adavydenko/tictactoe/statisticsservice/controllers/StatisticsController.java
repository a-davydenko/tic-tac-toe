package com.adavydenko.tictactoe.statisticsservice.controllers;

import com.adavydenko.tictactoe.statisticsservice.entities.GeneralStatistics;
import com.adavydenko.tictactoe.statisticsservice.entities.PlayerStatistics;
import com.adavydenko.tictactoe.statisticsservice.services.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@AllArgsConstructor
public class StatisticsController {

    private StatisticsService statisticsService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PlayerStatistics getPlayerStatistics(@RequestParam String playerId) {
        return statisticsService.getPlayerStatisticsByPlayerId(playerId);
    }

    @GetMapping("/general")
    @ResponseStatus(HttpStatus.OK)
    public GeneralStatistics getGeneralStatistics() {
        return statisticsService.getGeneralStatistics();
    }

    @GetMapping("/rating")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerStatistics> getRating() {
        return statisticsService.getRating();
    }

}
