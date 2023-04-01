package com.adavydenko.userservice;

import com.adavydenko.CommonConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @GetMapping("/user-service")
    public String sayHello(@RequestParam(value = "myName", defaultValue = CommonConstants.AUTHOR_NAME) String name) {
        return String.format("User service started by %s", name);
    }

}
