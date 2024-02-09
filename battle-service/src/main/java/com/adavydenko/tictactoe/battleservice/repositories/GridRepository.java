package com.adavydenko.tictactoe.battleservice.repositories;

import com.adavydenko.tictactoe.battleservice.entities.Grid;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GridRepository extends CrudRepository<Grid, UUID> {
}
