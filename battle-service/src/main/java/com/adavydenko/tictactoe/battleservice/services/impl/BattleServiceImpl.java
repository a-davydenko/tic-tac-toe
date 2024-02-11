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
        validateBattle(battleId, battle, BattleStatus.NEW);

        User player = userService.findById(userId);
        validatePlayerCanJoin(userId, player, battle);

        battle.setPlayerO(player);
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
        validateBattle(battleId, battle, BattleStatus.IN_PROGRESS);

        User player = userService.findById(userId);
        validatePlayerCanMakeStep(userId, player, battle);

        Step step = new Step();
        step.setBattle(battle);
        step.setPlayer(player);
        step.setX(x);
        step.setY(y);
        step.setStepDateTime(LocalDateTime.now());
        validateStep(step, battle);

        battle.addStep(step);
        // todo check if battle is finished

        return stepRepository.save(step);
    }

    @Override
    public Battle surrender(UUID battleId, UUID userId) {
        Battle battle = battleRepository.findById(battleId).orElse(null);
        validateBattle(battleId, battle, BattleStatus.IN_PROGRESS);

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

    private void validateBattle(UUID battleId, Battle battle, BattleStatus expectedStatus) {
        if (battle == null) {
            throw new IllegalArgumentException("Battle [" + battleId + "] is not found.");
        }
        if (battle.getStatus() != expectedStatus) {
            throw new IllegalArgumentException(String.format("Battle [%s] has status %s, but expected %s",
                    battleId, battle.getStatus().toString(), expectedStatus.toString()));
        }
        if (battle.getStatus() == BattleStatus.NEW && battle.getPlayerX() == null) {
            throw new IllegalArgumentException("There is no player X in battle [" + battleId + "]");
        }
        if (battle.getStatus() == BattleStatus.IN_PROGRESS && (battle.getPlayerX() == null || battle.getPlayerO() == null)) {
            throw new IllegalArgumentException("There is no player X or player O in battle [" + battleId + "]");
        }
    }

    private void validatePlayerCanJoin(UUID userId, User player, Battle battle) {
        if (player == null) {
            throw new IllegalArgumentException("User with id " + userId + " is not found.");
        }
        if (battle.getPlayerX() == player){
            throw new IllegalArgumentException("Player [" + userId + "] has already joined this battle.");
        }
    }

    private void validatePlayerCanMakeStep(UUID userId, User player, Battle battle) {
        if (player == null) {
            throw new IllegalArgumentException("User with id " + userId + " is not found.");
        }
        if (battle.getPlayerX() != player && battle.getPlayerO() != player) {
            throw new IllegalArgumentException("User with id " + userId + " is not a player in this battle.");
        }
        List<Step> steps = battle.getGrid().getSteps();
        Step lastStep = steps.size() > 0 ? steps.get(steps.size() - 1) : null;
        if (lastStep != null && lastStep.getPlayer() == player) {
            throw new IllegalArgumentException("Another user should make a step");
        }
    }

    private void validateStep(Step step, Battle battle) {
        Grid grid = battle.getGrid();
        int x = step.getX();
        int y = step.getY();
        if (x > grid.getSize() || x <= 0 || y > grid.getSize() || y <= 0) {
            throw new IllegalArgumentException(String.format("Step (%d, %d) is out of grid", x, y));
        }

        List<Step> steps = grid.getSteps();
        if (steps.stream().anyMatch(s -> s.getX() == x && s.getY() == y)) {
            throw new IllegalArgumentException(String.format("Cell (%d, %d) is occupied", x, y));
        }
    }
}
