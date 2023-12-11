package com.adavydenko.tictactoe.battleservice.entities;

import com.adavydenko.tictactoe.userservice.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Step {
    @Id
    private UUID id;
    @ManyToOne
    private Grid grid;
    @ManyToOne
    private User player;
    private int x;
    private int y;
    private LocalDateTime stepDateTime;
}
