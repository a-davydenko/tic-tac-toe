package com.adavydenko.tictactoe.battleservice.services.impl;

import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import com.adavydenko.tictactoe.battleservice.entities.Step;
import com.adavydenko.tictactoe.battleservice.repositories.BattleRepository;
import com.adavydenko.tictactoe.battleservice.repositories.StepRepository;
import com.adavydenko.tictactoe.userservice.entities.User;
import com.adavydenko.tictactoe.userservice.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class BattleServiceImplTest {
    @Mock
    UserService userService;
    @Mock
    BattleRepository battleRepository;
    @Mock
    StepRepository stepRepository;

    @InjectMocks
    BattleServiceImpl battleService;

    @Test
    void createBattle_userNotFound_test() {
        UUID userId = UUID.randomUUID();
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.createBattle(userId, 3, 3));
        Mockito.verify(userService, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verifyNoInteractions(battleRepository);
    }

    @Test
    void createBattle_incorrectWinSize_test() {
        User user = new User();
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(user);

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.createBattle(userId, 3, 4));
        Mockito.verify(userService, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verifyNoInteractions(battleRepository);
    }

    @Test
    void createBattle_ok_test() {
        User user = new User();
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        Battle battle = new Battle(user, 3, 3);
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(user);
        Mockito.when(battleRepository.save(Mockito.any(Battle.class))).thenReturn(battle);

        Battle savedBattle = battleService.createBattle(userId, 3, 3);

        Assertions.assertEquals(savedBattle, battle);
        Mockito.verify(userService, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verify(battleRepository, Mockito.times(1)).save(Mockito.any(Battle.class));
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void joinBattle_battleNotFound_test() {
        UUID userId = UUID.randomUUID();
        UUID battleId = UUID.randomUUID();
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.joinBattle(battleId, userId));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    void joinBattle_battleHasUnexpectedStatus_test() {
        UUID userId = UUID.randomUUID();
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle();
        battle.setId(battleId);
        battle.setStatus(BattleStatus.FINISHED);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.joinBattle(battleId, userId));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    void joinBattle_battleWithoutPlayers_test() {
        UUID userId = UUID.randomUUID();
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle();
        battle.setId(battleId);
        battle.setStatus(BattleStatus.NEW);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.joinBattle(battleId, userId));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    void joinBattle_userNotFound_test() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle();
        battle.setId(battleId);
        battle.setStatus(BattleStatus.NEW);
        battle.setPlayerX(user);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.joinBattle(battleId, userId));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verify(userService, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void joinBattle_userIsPlayerOfBattle_test() {
        UUID newPlayerId = UUID.randomUUID();
        User playerX = new User();
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle();
        battle.setId(battleId);
        battle.setStatus(BattleStatus.NEW);
        battle.setPlayerX(playerX);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(playerX);

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.joinBattle(battleId, newPlayerId));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verify(userService, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void joinBattle_ok_test() {
        UUID newPlayerId = UUID.randomUUID();
        User playerO = new User();
        User playerX = new User();
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle();
        battle.setId(battleId);
        battle.setStatus(BattleStatus.NEW);
        battle.setPlayerX(playerX);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(playerO);

        Battle updatedBattle = battleService.joinBattle(battleId, newPlayerId);

        Assertions.assertEquals(updatedBattle.getStatus(), BattleStatus.IN_PROGRESS);
        Mockito.verify(battleRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verify(userService, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verify(battleRepository, Mockito.times(1)).save(Mockito.any(Battle.class));
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void findBattles_test() {
        List<Battle> battles = battleService.findBattles();

        Assertions.assertTrue(battles.isEmpty());
        Mockito.verify(battleRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void findBattlesByStatus_test() {
        List<Battle> battles = battleService.findBattlesByStatus(BattleStatus.IN_PROGRESS);

        Assertions.assertTrue(battles.isEmpty());
        Mockito.verify(battleRepository, Mockito.times(1)).findAllByStatus(BattleStatus.IN_PROGRESS);
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void findBattlesByUserId_test() {
        UUID userId = UUID.randomUUID();
        List<Battle> battles = battleService.findBattlesByUserId(userId);

        Assertions.assertTrue(battles.isEmpty());
        Mockito.verify(battleRepository, Mockito.times(1)).findAllByUserId(userId);
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void findBattleByBattleId_test() {
        UUID battleId = UUID.randomUUID();
        Battle battle = battleService.findBattleByBattleId(battleId);

        Assertions.assertNull(battle);
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void makeStep_battleNotFound_test() {
        UUID battleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.makeStep(battleId, userId, 1, 1));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verifyNoInteractions(userService, stepRepository);
    }

    @Test
    void makeStep_battleHasUnexpectedStatus_test() {
        UUID battleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Battle battle = new Battle(battleId, BattleStatus.FINISHED, null, null, null, 3, 3, null, null, null);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.makeStep(battleId, userId, 1, 1));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verifyNoInteractions(userService, stepRepository);
    }

    @Test
    void makeStep_battleWithoutPlayers_test() {
        UUID userId = UUID.randomUUID();
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle(battleId, BattleStatus.IN_PROGRESS, null, null, null, 3, 3, null, null, null);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.makeStep(battleId, userId, 1, 1));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verifyNoInteractions(userService, stepRepository);
    }

    @Test
    void makeStep_userNotFound_test() {
        UUID userId = UUID.randomUUID();
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle(battleId, BattleStatus.IN_PROGRESS, playerX, playerO, null, 3, 3, null, null, null);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.makeStep(battleId, userId, 1, 1));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verify(userService, Mockito.times(1)).findById(userId);
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verifyNoInteractions(stepRepository);
    }

    @Test
    void makeStep_userIsNotPlayerOfBattle_test() {
        UUID userId = UUID.randomUUID();
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle(battleId, BattleStatus.IN_PROGRESS, playerX, playerO, null, 3, 3, null, null, null);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(new User());

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.makeStep(battleId, userId, 1, 1));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verify(userService, Mockito.times(1)).findById(userId);
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verifyNoInteractions(stepRepository);
    }

    @Test
    void makeStep_anotherPlayerShouldMakeStep_test() {
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle(battleId, BattleStatus.IN_PROGRESS, playerX, playerO, null, 3, 3, null, null, null);
        Step step = new Step();
        step.setPlayer(playerX);
        battle.addStep(step);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(playerX);

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.makeStep(battleId, playerXId, 1, 1));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verify(userService, Mockito.times(1)).findById(playerXId);
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verifyNoInteractions(stepRepository);
    }

    @Test
    void makeStep_stepOutOfGrid_test() {
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle(battleId, BattleStatus.IN_PROGRESS, playerX, playerO, null, 3, 3, null, null, null);
        Step step = new Step();
        step.setPlayer(playerX);
        battle.addStep(step);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(playerO);

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.makeStep(battleId, playerOId, 0, 0));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verify(userService, Mockito.times(1)).findById(playerOId);
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verifyNoInteractions(stepRepository);
    }

    @Test
    void makeStep_cellIsOccupied_test() {
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle(battleId, BattleStatus.IN_PROGRESS, playerX, playerO, null, 3, 3, null, null, null);
        Step step = new Step(UUID.randomUUID(), battle, playerX, 1, 1, null);
        battle.addStep(step);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(playerO);

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.makeStep(battleId, playerOId, 1, 1));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verify(userService, Mockito.times(1)).findById(playerOId);
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verifyNoInteractions(stepRepository);
    }

    @Test
    void makeStep_battleIsNotFinished_notEnoughSteps_test() {
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle(battleId, BattleStatus.IN_PROGRESS, playerX, playerO, null, 3, 3, null, null, null);
        Step step = new Step(UUID.randomUUID(), battle, playerX, 2, 1, null);
        battle.addStep(step);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(playerO);
        Mockito.when(stepRepository.save(Mockito.any(Step.class))).thenReturn(new Step(UUID.randomUUID(), battle, playerO, 1, 1 , null));

        battleService.makeStep(battleId, playerOId, 1, 1);

        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verify(userService, Mockito.times(1)).findById(playerOId);
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verify(stepRepository, Mockito.times(1)).save(Mockito.any(Step.class));
        Mockito.verifyNoMoreInteractions(stepRepository);
    }

    @Test
    void makeStep_battleIsNotFinished_test() {
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle(battleId, BattleStatus.IN_PROGRESS, playerX, playerO, null, 3, 3, null, null, null);
        Step step = new Step(UUID.randomUUID(), battle, playerX, 2, 1, null);
        battle.addStep(new Step(UUID.randomUUID(), battle, playerO, 1, 1 , null));
        battle.addStep(new Step(UUID.randomUUID(), battle, playerX, 2, 1 , null));
        battle.addStep(new Step(UUID.randomUUID(), battle, playerO, 2, 2 , null));
        battle.addStep(new Step(UUID.randomUUID(), battle, playerX, 3, 3 , null));
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(playerO);
        Mockito.when(stepRepository.save(Mockito.any(Step.class))).thenReturn(new Step(UUID.randomUUID(), battle, playerO, 1, 3 , null));

        battleService.makeStep(battleId, playerOId, 1, 3);

        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
        Mockito.verify(userService, Mockito.times(1)).findById(playerOId);
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verify(stepRepository, Mockito.times(1)).save(Mockito.any(Step.class));
        Mockito.verifyNoMoreInteractions(stepRepository);
    }

    @Test
    void makeStep_battleIsFinished_test() {
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle(battleId, BattleStatus.IN_PROGRESS, playerX, playerO, null, 3, 3, null, null, null);
        new Step(UUID.randomUUID(), battle, playerX, 2, 1, null);
        battle.addStep(new Step(UUID.randomUUID(), battle, playerO, 1, 1 , null));
        battle.addStep(new Step(UUID.randomUUID(), battle, playerX, 2, 1 , null));
        battle.addStep(new Step(UUID.randomUUID(), battle, playerO, 2, 2 , null));
        battle.addStep(new Step(UUID.randomUUID(), battle, playerX, 3, 1 , null));
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(userService.findById(Mockito.any(UUID.class))).thenReturn(playerO);
        Mockito.when(stepRepository.save(Mockito.any(Step.class))).thenReturn(new Step(UUID.randomUUID(), battle, playerO, 3, 3 , null));

        Battle updatedBattle = battleService.makeStep(battleId, playerOId, 3, 3);

        Assertions.assertEquals(updatedBattle.getStatus(), BattleStatus.FINISHED);
        Assertions.assertEquals(updatedBattle.getWinner(), playerO);
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verify(userService, Mockito.times(1)).findById(playerOId);
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verify(stepRepository, Mockito.times(1)).save(Mockito.any(Step.class));
        Mockito.verifyNoMoreInteractions(stepRepository);
        Mockito.verify(battleRepository, Mockito.times(1)).save(battle);
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void surrender_battleNotFound_test() {
        UUID battleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.surrender(battleId, userId));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void surrender_battleHasUnexpectedStatus_test() {
        UUID userId = UUID.randomUUID();
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle();
        battle.setId(battleId);
        battle.setStatus(BattleStatus.FINISHED);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.surrender(battleId, userId));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void surrender_battleWithoutPlayers_test() {
        UUID userId = UUID.randomUUID();
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle();
        battle.setId(battleId);
        battle.setStatus(BattleStatus.IN_PROGRESS);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.surrender(battleId, userId));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void surrender_userIsNotPlayerOfBattle_test() {
        UUID userId = UUID.randomUUID();
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle();
        battle.setId(battleId);
        battle.setStatus(BattleStatus.IN_PROGRESS);
        battle.setPlayerX(playerX);
        battle.setPlayerO(playerO);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));

        Assertions.assertThrows(IllegalArgumentException.class, () -> battleService.surrender(battleId, userId));
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void surrender_playerX_test() {
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle();
        battle.setId(battleId);
        battle.setStatus(BattleStatus.IN_PROGRESS);
        battle.setPlayerX(playerX);
        battle.setPlayerO(playerO);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(battleRepository.save(Mockito.any(Battle.class))).thenReturn(battle);

        Battle updatedBattle = battleService.surrender(battleId, playerXId);

        Assertions.assertEquals(updatedBattle.getStatus(), BattleStatus.FINISHED);
        Assertions.assertEquals(updatedBattle.getWinner(), playerO);
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verify(battleRepository, Mockito.times(1)).save(battle);
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void surrender_playerO_test() {
        UUID playerXId = UUID.randomUUID();
        User playerX = new User();
        playerX.setId(playerXId);
        UUID playerOId = UUID.randomUUID();
        User playerO = new User();
        playerO.setId(playerOId);
        UUID battleId = UUID.randomUUID();
        Battle battle = new Battle();
        battle.setId(battleId);
        battle.setStatus(BattleStatus.IN_PROGRESS);
        battle.setPlayerX(playerX);
        battle.setPlayerO(playerO);
        Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
        Mockito.when(battleRepository.save(Mockito.any(Battle.class))).thenReturn(battle);

        Battle updatedBattle = battleService.surrender(battleId, playerOId);

        Assertions.assertEquals(updatedBattle.getStatus(), BattleStatus.FINISHED);
        Assertions.assertEquals(updatedBattle.getWinner(), playerX);
        Mockito.verify(battleRepository, Mockito.times(1)).findById(battleId);
        Mockito.verify(battleRepository, Mockito.times(1)).save(battle);
        Mockito.verifyNoMoreInteractions(battleRepository);
    }

    @Test
    void sortSteps_test() {
        List<Step> steps = generateSteps();
        battleService.sortSteps(steps);

        Assertions.assertEquals(steps.get(0).getX(), 1);
        Assertions.assertEquals(steps.get(0).getY(), 1);

        Assertions.assertEquals(steps.get(1).getX(), 1);
        Assertions.assertEquals(steps.get(1).getY(), 2);

        Assertions.assertEquals(steps.get(2).getX(), 1);
        Assertions.assertEquals(steps.get(2).getY(), 3);

        Assertions.assertEquals(steps.get(3).getX(), 2);
        Assertions.assertEquals(steps.get(3).getY(), 1);

        Assertions.assertEquals(steps.get(4).getX(), 2);
        Assertions.assertEquals(steps.get(4).getY(), 2);
    }

    @Test
    void calculateNOfStepsInRow_test() {
        List<Step> steps = generateSteps();
        Step currentStep = steps.stream().filter(step -> step.getX() == 1 && step.getY() == 1).findFirst().orElse(null);

        Assertions.assertEquals(battleService.calculateNOfStepsInRow(currentStep, steps, 1, 0), 2);
        Assertions.assertEquals(battleService.calculateNOfStepsInRow(currentStep, steps, 0, 1), 3);
        Assertions.assertEquals(battleService.calculateNOfStepsInRow(currentStep, steps, 1, 1), 2);
        Assertions.assertEquals(battleService.calculateNOfStepsInRow(currentStep, steps, 1, -1), 1);
    }

    private List<Step> generateSteps() {
        /** Grid:
         *    1 2 3
         *    - - -
         * 1| 1 1 0
         * 2| 1 1 0
         * 3| 1 0 0
         * */
        Step step11 = new Step();
        step11.setX(1);
        step11.setY(1);
        Step step12 = new Step();
        step12.setX(1);
        step12.setY(2);
        Step step13 = new Step();
        step13.setX(1);
        step13.setY(3);
        Step step21 = new Step();
        step21.setX(2);
        step21.setY(1);
        Step step22 = new Step();
        step22.setX(2);
        step22.setY(2);

        return Arrays.asList(step13, step22, step12, step21, step11);
    }
}