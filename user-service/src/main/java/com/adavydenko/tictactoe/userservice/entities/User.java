package com.adavydenko.tictactoe.userservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private LocalDateTime lastSeenOnline;

    public void merge(User newUser) {
        if (!StringUtils.isBlank(newUser.getUsername())) this.username = newUser.getUsername();
        if (!StringUtils.isBlank(newUser.getEmail())) this.email = newUser.getEmail();
        this.setLastSeenOnline(LocalDateTime.now());
    }
}
