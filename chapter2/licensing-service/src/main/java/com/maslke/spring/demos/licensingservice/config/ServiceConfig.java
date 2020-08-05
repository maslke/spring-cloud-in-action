package com.maslke.spring.demos.licensingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ServiceConfig {
    @Value("${signing.key}")
    private String jwtSigningKey = "";

    @Value("${redis.server}")
    private String redisServer;

    @Value("${redis.port}")
    private Integer redisPort;

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public String getRedisServer() {
        return this.redisServer;
    }

    public int getRedisPort() {
        return this.redisPort;
    }
}

