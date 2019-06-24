package com.maslke.spring.demos.simpleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/hello")
public class SimpleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleServiceApplication.class, args);
    }

    @RequestMapping(value = "/{firstName}/{secondName}", method = RequestMethod.GET)
    public String hello(@PathVariable String firstName, @PathVariable String secondName) {
        return String.format("{\"firstName\":%s,\"secondName\":%s}", firstName, secondName);
    }

}
