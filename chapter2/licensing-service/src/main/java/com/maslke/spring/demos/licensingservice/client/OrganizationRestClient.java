package com.maslke.spring.demos.licensingservice.client;

import com.maslke.spring.demos.licensingservice.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrganizationRestClient {

    private RestTemplate restTemplate;

    @Autowired
    public OrganizationRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Organization getOrganization(String organizationId) {
        //String serviceUri = "http://organizationservice/v1/organizations/{organizationId}";
        // 此处是使用的eureka的注册地址。
        // 如果 zuulservice没有注册到eureka的话，此处可以使用zuulservice的真实部署地址。如http://localhost:5555/api/organization/v1///
        String serviceUri = "http://zuulservice/api/organization/v1/organizations/{organizationId}";
        ResponseEntity<Organization> exchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null, Organization.class, organizationId);
        return exchange.getBody();
    }
}
