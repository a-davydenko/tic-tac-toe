package com.adavydenko.tictactoe.battleservice.services.impl;

import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import com.adavydenko.tictactoe.battleservice.entities.Grid;
import com.adavydenko.tictactoe.battleservice.entities.Step;
import com.adavydenko.tictactoe.battleservice.repositories.BattleRepository;
import com.adavydenko.tictactoe.battleservice.repositories.GridRepository;
import com.adavydenko.tictactoe.battleservice.repositories.StepRepository;
import com.adavydenko.tictactoe.battleservice.services.BattleService;
import com.adavydenko.tictactoe.userservice.entities.User;
import com.adavydenko.tictactoe.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class BattleServiceImpl implements BattleService {

    private UserService userService;
    private BattleRepository battleRepository;
    private GridRepository gridRepository;
    private StepRepository stepRepository;

    @Override
    public Battle createBattle(UUID userId, int gridSize, int winSize) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with id " + userId + " is not found.");
        }

        Grid grid = gridRepository.save(new Grid(gridSize, winSize));
        Battle battle = new Battle(user, grid);
        battleRepository.save(battle);

        return battle;
    }

    @Override
    public Battle joinBattle(UUID battleId, UUID userId) {
        Battle battle = battleRepository.findById(battleId).orElse(null);
        // todo add check if battle is available to join

        if (battle == null) {
            throw new IllegalArgumentException("Battle [" + battleId + "] is not found.");
        }

        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with id " + userId + " is not found.");
        }

        battle.setPlayerO(user);
        battle.setStatus(BattleStatus.IN_PROGRESS);
        battle.setStartDateTime(LocalDateTime.now());
        battleRepository.save(battle);

        return battle;
    }

    @Override
    public List<Battle> findBattles() {
        return StreamSupport.stream(battleRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<Battle> findBattlesByStatus(BattleStatus status) {
        return battleRepository.findAllByStatus(status);
    }

    @Override
    public List<Battle> findBattlesByUserId(UUID userId) {
        return battleRepository.findAllByUserId(userId);
    }

    @Override
    public Battle findBattleByBattleId(UUID battleId) {
        return battleRepository.findById(battleId).orElse(null);
    }

    @Override
    public Step makeStep(UUID battleId, UUID userId, int x, int y) {
        Battle battle = battleRepository.findById(battleId).orElse(null);
        validateBattle(battleId, battle);

        User player = userService.findById(userId);
        Step step = new Step();
        step.setBattle(battle);
        step.setPlayer(player);
        step.setX(x);
        step.setY(y);

        // todo add step validation

        battle.addStep(step);

        return stepRepository.save(step);
    }

    @Override
    public Battle surrender(UUID battleId, UUID userId) {
        Battle battle = battleRepository.findById(battleId).orElse(null);
        validateBattle(battleId, battle);

        if (battle.getPlayerX().getId().equals(userId)) {
            battle.setWinner(battle.getPlayerO());
        } else if (battle.getPlayerO().getId().equals(userId)) {
            battle.setWinner(battle.getPlayerX());
        } else {
            throw new IllegalArgumentException("User with id " + userId + " is not a player in this battle.");
        }

        battle.setEndDateTime(LocalDateTime.now());
        battle.setStatus(BattleStatus.FINISHED);

        return battleRepository.save(battle);
    }

    private void validateBattle(UUID battleId, Battle battle) {
        if (battle == null) {
            throw new IllegalArgumentException("Battle [" + battleId + "] is not found.");
        }

        if (battle.getPlayerX() == null || battle.getPlayerO() == null) {
            throw new IllegalArgumentException("Battle [" + battleId + "] is not valid.");
        }
    }
}
