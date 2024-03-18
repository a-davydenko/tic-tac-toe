package com.adavydenko.tictactoe.battleservice.repositories;

import com.adavydenko.tictactoe.battleservice.entities.Step;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StepRepository extends CrudRepository<Step, UUID> {
}
