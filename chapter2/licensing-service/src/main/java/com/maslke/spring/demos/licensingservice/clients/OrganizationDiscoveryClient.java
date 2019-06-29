package com.maslke.spring.demos.licensingservice.clients;

import com.maslke.spring.demos.licensingservice.model.Organization;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author:maslke
 * @date:2019/6/29
 * @version:0.0.1
 */
@Component
public class OrganizationDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
        if (instances.isEmpty()) {
            return null;
        }
        String serviceUrl = String.format("%s/v1/organizations/%s", instances.get(0).getUri().toString(),
                organizationId);
        System.out.println(serviceUrl);
        ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUrl, HttpMethod.GET,
                 null, Organization.class, organizationId);
        return restExchange.getBody();
    }






}

