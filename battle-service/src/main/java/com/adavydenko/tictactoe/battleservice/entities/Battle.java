package com.adavydenko.tictactoe.battleservice.entities;

import com.adavydenko.tictactoe.userservice.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "battle")
    private List<Step> steps;
    private int size;
    private int winSize;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private User winner;

    public Battle(User playerX, int size, int winSize) {
        this.playerX = playerX;
        this.size = size;
        this.winSize = winSize > 0 ? winSize : size;
        this.setStatus(BattleStatus.NEW);
    }

    @JsonIgnore
    public boolean addStep(@NonNull Step step) {
        if (steps == null) {
            steps = new ArrayList<>();
        }

        return steps.add(step);
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
