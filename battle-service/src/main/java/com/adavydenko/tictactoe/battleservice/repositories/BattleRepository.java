package com.adavydenko.tictactoe.battleservice.repositories;

import com.adavydenko.tictactoe.battleservice.entities.Battle;
import com.adavydenko.tictactoe.battleservice.entities.BattleStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BattleRepository extends CrudRepository<Battle, UUID> {

    List<Battle> findAllByStatus(BattleStatus status);

    @Query(value = "select * from battle b where b.player_x_id = ?1 or b.player_o_id = ?1", nativeQuery = true)
    List<Battle> findAllByUserId(UUID userId);

//    Step saveStep(Step step);
}
