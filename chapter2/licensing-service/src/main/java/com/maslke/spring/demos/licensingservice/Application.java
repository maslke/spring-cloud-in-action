package com.maslke.spring.demos.licensingservice;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class Application implements ApplicationRunner, CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.print("run...");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<String> names = args.getOptionNames();
        System.out.print("names:" + names);
    }
}
