package com.adavydenko.tictactoe.battleservice.services;

import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import com.adavydenko.tictactoe.battleservice.entities.Step;

import java.util.List;
import java.util.UUID;

public interface BattleService {

    Battle createBattle(UUID userId, int gridSize, int winSize);

    Battle joinBattle(UUID battleId, UUID userId);

    List<Battle> findBattles();

    List<Battle> findBattlesByStatus(BattleStatus status);

    List<Battle> findBattlesByUserId(UUID userId);

    Battle findBattleByBattleId(UUID battleId);

    Step makeStep(UUID battleId, UUID userId, int x, int y);

    Battle surrender(UUID battleId, UUID userId);
}
