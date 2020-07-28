package com.maslke.spring.demos.organizationservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // @RefreshScope 添加到ServiceConfig中，在nacos中更改了 nacos.key的配置，才能有作用。
public class ServiceConfig {

    @Value("${nacos.key}")
    private String key;

    public String getKey() {
        return this.key;
    }
}
