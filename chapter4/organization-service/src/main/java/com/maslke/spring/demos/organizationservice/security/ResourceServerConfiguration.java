package com.maslke.spring.demos.organizationservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author:maslke
 * @date:2019/7/11
 * @version:0.0.1
 */
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/v1/organizations/**")
                .hasRole("ADMIN").anyRequest().authenticated();
    }
}
