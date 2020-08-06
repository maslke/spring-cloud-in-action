package com.maslke.spring.demos.licensingservice;

import brave.sampler.Sampler;
import com.maslke.spring.demos.licensingservice.config.ServiceConfig;
import com.maslke.spring.demos.licensingservice.event.InOrgChangeSource;
import com.maslke.spring.demos.licensingservice.model.OrganizationChangeModel;
import com.maslke.spring.demos.licensingservice.repository.OrganizationRedisRepositoryImpl;
import com.maslke.spring.demos.licensingservice.util.UserContextInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@EnableResourceServer
@EnableBinding(InOrgChangeSource.class)
public class Application implements ApplicationRunner, CommandLineRunner {

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private OrganizationRedisRepositoryImpl redisRepository;


    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // 在organizationservice调用需要Authorization Header的时候，要想将licensingservice的Authorization Header传递到调用organizationservice中
    // 同样可以使用UserContext的方式
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 去除自定义的拦截器之后，使用rest 客户端来进行调用，将无法正常调用organizationservice，因为没有传递Authorization Header过去
        // 用来和使用OAuth2RestTemplate的方式进行对比
        restTemplate.getInterceptors().add(new UserContextInterceptor());
        return restTemplate;
    }


    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(serviceConfig.getRedisServer());
        jedisConnectionFactory.setPort(serviceConfig.getRedisPort());
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

//    @Bean
//    @LoadBalanced
//    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oAuth2ClientContext,
//                                                 OAuth2ProtectedResourceDetails details) {
//        return new OAuth2RestTemplate(details, oAuth2ClientContext);
//    }


    @StreamListener(InOrgChangeSource.ORGCHANGE)
    public void listener(OrganizationChangeModel model) {
        logger.info("Organization {} has changed:{}", model.getOrganizationId(), model.getActionName());
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
