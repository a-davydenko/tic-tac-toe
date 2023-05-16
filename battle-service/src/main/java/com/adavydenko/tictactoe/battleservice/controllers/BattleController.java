package com.adavydenko.tictactoe.battleservice.controllers;

import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import com.adavydenko.tictactoe.battleservice.entities.Step;
import com.adavydenko.tictactoe.battleservice.services.BattleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/battle")
@AllArgsConstructor
public class BattleController {

    private BattleService battleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Battle createBattle(@RequestBody String userId) {
        return battleService.createBattle(userId);
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
    public List<Battle> getBattlesByUserId(@RequestParam String userId) {
        return battleService.getBattlesByUserId(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Battle getBattleByBattleId(@RequestParam String battleId) {
        return battleService.getBattleByBattleId(battleId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Battle makeStep(@RequestBody String battleId, @RequestBody String userId, @RequestBody Step step) {
        return battleService.makeStep(battleId, userId, step);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Battle surrender(@RequestBody String battleId, @RequestBody String userId) {
        return battleService.finishBattle(battleId, userId);
    }
}
