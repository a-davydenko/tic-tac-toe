package com.adavydenko.tictactoe.battleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"com.adavydenko.tictactoe.battleservice.repositories", "com.adavydenko.tictactoe.userservice.repositories"})
@ComponentScan({"com.adavydenko.tictactoe.battleservice", "com.adavydenko.tictactoe.userservice"})
public class BattleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BattleServiceApplication.class, args);
    }

}
