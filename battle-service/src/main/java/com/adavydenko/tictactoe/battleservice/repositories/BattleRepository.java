package com.adavydenko.tictactoe.battleservice.repositories;

import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import com.adavydenko.tictactoe.battleservice.entities.Step;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BattleRepository {

    Battle saveBattle(Battle battle);

    List<Battle> getAllBattles();

    List<Battle> getBattlesByStatus(BattleStatus status);

    List<Battle> getBattlesByUserId(String userId);

    Battle getBattleByBattleId(String battleId);

    Battle updateBattle(Battle battle);

    Step saveStep(Step step);
}
