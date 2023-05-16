package com.adavydenko.tictactoe.battleservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Step {
    @Id
    private String id;
    private Coordinate x;
    private Coordinate y;
    private LocalDateTime stepDateTime;
}
