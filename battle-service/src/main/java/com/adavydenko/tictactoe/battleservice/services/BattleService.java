package com.adavydenko.tictactoe.battleservice.services;

import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import com.adavydenko.tictactoe.battleservice.entities.Step;

import java.util.List;

public interface BattleService {

    Battle createBattle(String userId, int gridSize, int winSize);

    Battle joinBattle(String battleId, String userId);

    List<Battle> getAllBattles();

    List<Battle> getBattlesByStatus(BattleStatus status);

    List<Battle> getBattlesByUserId(String userId);

    Battle getBattleByBattleId(String battleId);

    Step makeStep(String battleId, Step step);

    Battle finishBattle(String battleId, String userId);
}
