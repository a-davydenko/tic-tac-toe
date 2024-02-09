package com.adavydenko.tictactoe.battleservice.entities;

import com.adavydenko.tictactoe.userservice.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Step {
    @Id
    @UuidGenerator
    @Column(name = "STEP_ID")
    private UUID id;
    @JsonIgnore
    @ManyToOne
    private Battle battle;
    @ManyToOne
    private User player;
    private int x;
    private int y;
    private LocalDateTime stepDateTime;
}
