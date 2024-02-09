package com.adavydenko.tictactoe.battleservice.controllers;

import com.adavydenko.tictactoe.battleservice.dto.CreateBattleDTO;
import com.adavydenko.tictactoe.battleservice.dto.StepDTO;
import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import com.adavydenko.tictactoe.battleservice.entities.Step;
import com.adavydenko.tictactoe.battleservice.services.BattleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class BattleController {

    private BattleService battleService;

    @PostMapping("/battle/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Battle createBattle(@RequestBody CreateBattleDTO createBattleDTO) {
        return battleService.createBattle(createBattleDTO.getUserId(), createBattleDTO.getGridSize(), createBattleDTO.getWinSize());
    }

    @GetMapping("/battles")
    @ResponseStatus(HttpStatus.OK)
    public List<Battle> getAllBattles() {
        return battleService.findBattles();
    }

    @GetMapping("/battles/open")
    @ResponseStatus(HttpStatus.OK)
    public List<Battle> getOpenBattles() {
        return battleService.findBattlesByStatus(BattleStatus.NEW);
    }

    @GetMapping("/battles/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Battle> getBattlesByUserId(@PathVariable UUID userId) {
        return battleService.findBattlesByUserId(userId);
    }

    @GetMapping("/battle/{battleId}")
    @ResponseStatus(HttpStatus.OK)
    public Battle getBattleByBattleId(@PathVariable UUID battleId) {
        return battleService.findBattleByBattleId(battleId);
    }

    @PutMapping("/battle/{battleId}/join")
    @ResponseStatus(HttpStatus.OK)
    public Battle joinBattle(@PathVariable UUID battleId, @RequestBody UUID userId) {
        return battleService.joinBattle(battleId, userId);
    }

    @PutMapping("/battle/{battleId}")
    @ResponseStatus(HttpStatus.OK)
    public Step makeStep(@PathVariable UUID battleId, @RequestBody StepDTO stepDTO) {
        return battleService.makeStep(battleId, stepDTO.getPlayerId(), stepDTO.getX(), stepDTO.getY());
    }

    @PutMapping("battle/{battleId}/surrender")
    @ResponseStatus(HttpStatus.OK)
    public Battle surrender(@PathVariable UUID battleId, @RequestBody UUID userId) {
        return battleService.surrender(battleId, userId);
    }
}
