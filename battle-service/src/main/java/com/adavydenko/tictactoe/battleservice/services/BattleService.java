package com.adavydenko.tictactoe.battleservice.services;

import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import com.adavydenko.tictactoe.battleservice.entities.Step;

import java.util.List;
import java.util.UUID;

public interface BattleService {

    Battle createBattle(UUID userId, int gridSize, int winSize);

    Battle joinBattle(UUID battleId, UUID userId);

    List<Battle> getAllBattles();

    List<Battle> getBattlesByStatus(BattleStatus status);

    List<Battle> getBattlesByUserId(UUID userId);

    Battle getBattleByBattleId(UUID battleId);

    Step makeStep(UUID battleId, Step step);

    Battle finishBattle(UUID battleId, UUID userId);
}
