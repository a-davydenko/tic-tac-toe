package com.adavydenko.tictactoe.battleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BattleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BattleServiceApplication.class, args);
    }

    @GetMapping("/battle-service")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "a-davydenko") String name) {
        return String.format("Battle service started by %s", name);
    }

}
