package com.adavydenko.statisticsservice;

import com.adavydenko.CommonConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class StatisticsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsServiceApplication.class, args);
    }

    @GetMapping("/statistics-service")
    public String sayHello(@RequestParam(value = "myName", defaultValue = CommonConstants.AUTHOR_NAME) String name) {
        return String.format("Statistics service started by %s", name);
    }

}
