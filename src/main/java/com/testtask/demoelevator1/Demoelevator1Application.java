package com.testtask.demoelevator1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demoelevator1Application implements CommandLineRunner {
    @Autowired
    private ElevatorService elevatorService;


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Demoelevator1Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }

    @Override
    public void run(String... args) throws Exception {
        elevatorService.start();
    }
}
