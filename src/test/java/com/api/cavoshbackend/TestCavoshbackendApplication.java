package com.api.cavoshbackend;

import org.springframework.boot.SpringApplication;

public class TestCavoshbackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(CavoshbackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
