package com.adavydenko.tictactoe.battleservice.repositories;

import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import com.adavydenko.tictactoe.battleservice.entities.Step;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BattleRepository {

    Battle saveBattle(Battle battle);

    List<Battle> getAllBattles();

    List<Battle> getBattlesByStatus(BattleStatus status);

    List<Battle> getBattlesByUserId(UUID userId);

    Battle getBattleByBattleId(UUID battleId);

    Battle updateBattle(Battle battle);

    Step saveStep(Step step);
}
