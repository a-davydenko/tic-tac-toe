package com.adavydenko.tictactoe.battleservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Grid {
    @Id
    @UuidGenerator
    @Column(name = "grid_id")
    private UUID id;
    @OneToMany
    @JoinColumn(name = "battle_id")
    private List<Step> steps;
    private int size;
    private int winSize;

    public Grid(int gridSize, int winSize) {
        this.size = gridSize;
        this.winSize = winSize > 0 ? winSize : gridSize;
    }

    public boolean addStep(@NonNull Step step) {
        if (steps == null) {
            steps = new ArrayList<>();
        }

        return steps.add(step);
    }
}
