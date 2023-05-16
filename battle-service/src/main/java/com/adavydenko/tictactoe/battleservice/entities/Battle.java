package com.adavydenko.tictactoe.battleservice.entities;

import com.adavydenko.tictactoe.userservice.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Battle {
    @Id
    private String id;
    private BattleStatus status;
    @ManyToOne
    private User playerOne; // player X
    @ManyToOne
    private User playerTwo; // player O
    @OneToMany
    private List<Step> steps;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @ManyToOne
    private User winner;

    public Battle(User playerOne) {
        this.playerOne = playerOne;
    }

    public boolean addStep(Step step) {
        return steps.add(step);
    }
}
