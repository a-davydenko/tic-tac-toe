package com.adavydenko.tictactoe.battleservice.entities;

import com.adavydenko.tictactoe.userservice.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

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
    @UuidGenerator
    @Column(name = "battle_id")
    private UUID id;
    private BattleStatus status;
    @ManyToOne(targetEntity = com.adavydenko.tictactoe.userservice.entities.User.class)
    @JoinColumn(name = "player_x_id")
    private User playerX;
    @ManyToOne(targetEntity = com.adavydenko.tictactoe.userservice.entities.User.class)
    @JoinColumn(name = "player_o_id")
    private User playerO;
    @OneToOne
    @JoinColumn(name = "grid_id")
    private Grid grid;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private User winner;

    public Battle(User playerX, Grid grid) {
        this.playerX = playerX;
        this.grid = grid;
        this.setStatus(BattleStatus.NEW);
    }

    @JsonIgnore
    public boolean addStep(Step step) {
        return grid.addStep(step);
    }

    @JsonIgnore
    public int getNOfSteps() {
        return grid.getSteps().size();
    }

    @JsonIgnore
    public Duration getDuration() {
        return switch (status) {
            case NEW -> Duration.ZERO;
            case IN_PROGRESS -> Duration.between(startDateTime, LocalDateTime.now());
            case FINISHED -> Duration.between(startDateTime, endDateTime);
        };
    }
}
