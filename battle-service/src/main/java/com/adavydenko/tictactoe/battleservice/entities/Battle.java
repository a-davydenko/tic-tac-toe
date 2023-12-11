package com.adavydenko.tictactoe.battleservice.entities;

import com.adavydenko.tictactoe.userservice.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Battle {
    @Id
    private UUID id;
    private BattleStatus status;
    @ManyToOne
    private User playerX;
    @ManyToOne
    private User playerO;
    @OneToOne
    private Grid grid;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @ManyToOne
    private User winner;

    public Battle(User playerX, Grid grid) {
        this.playerX = playerX;
        this.grid = grid;
    }

    public boolean addStep(Step step) {
        return grid.addStep(step);
    }
}
