package com.maslke.spring.demos.zuulsvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient

public class ZuulsvrApplication {

//    @LoadBalanced
//    @Bean
//    public RestTemplate getRestTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
//        if (interceptors.isEmpty()) {
//            restTemplate.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
//        } else {
//            interceptors.add(new UserContextInterceptor());
//            restTemplate.setInterceptors(interceptors);
//        }
//        return restTemplate;
//    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulsvrApplication.class, args);
    }

}
