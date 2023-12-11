package com.adavydenko.tictactoe.battleservice.controllers;

import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import com.adavydenko.tictactoe.battleservice.entities.Step;
import com.adavydenko.tictactoe.battleservice.services.BattleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/battle")
@AllArgsConstructor
public class BattleController {

    private BattleService battleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Battle createBattle(@RequestBody UUID userId, @RequestBody int gridSize, @RequestBody int winSize) {
        return battleService.createBattle(userId, gridSize, winSize);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Battle> getAllBattles() {
        return battleService.getAllBattles();
    }

    @GetMapping("/open")
    @ResponseStatus(HttpStatus.OK)
    public List<Battle> getOpenBattles() {
        return battleService.getBattlesByStatus(BattleStatus.NEW);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Battle> getBattlesByUserId(@RequestParam UUID userId) {
        return battleService.getBattlesByUserId(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Battle getBattleByBattleId(@RequestParam UUID battleId) {
        return battleService.getBattleByBattleId(battleId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Step makeStep(@RequestBody UUID battleId, @RequestBody Step step) {
        return battleService.makeStep(battleId, step);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Battle surrender(@RequestBody UUID battleId, @RequestBody UUID userId) {
        return battleService.finishBattle(battleId, userId);
    }
}
