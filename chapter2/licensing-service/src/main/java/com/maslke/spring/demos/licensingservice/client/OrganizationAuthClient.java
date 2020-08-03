package com.maslke.spring.demos.licensingservice.client;

import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import com.maslke.spring.demos.licensingservice.model.Organization;

@Component
public class OrganizationAuthClient {

    private OAuth2RestTemplate oAuth2RestTemplate;

    public OrganizationAuthClient(@Autowired(required = false) OAuth2RestTemplate restTemplate) {
        this.oAuth2RestTemplate = restTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(OrganizationAuthClient.class);

    public Organization getOrganization(String organizationId) {
        // String serviceUri = "http://organizationservice/v1/organizations/{organizationId}";
        // 此处是使用的eureka的注册地址。
        // 如果 zuulservice没有注册到eureka的话，此处可以使用zuulservice的真实部署地址。如http://localhost:5555/api/organization/v1///
        String serviceUri = "http://zuulservice/api/organization/v1/organizations/{organizationId}";
        ResponseEntity<Organization> exchange = oAuth2RestTemplate.exchange(serviceUri, HttpMethod.GET, null, Organization.class, organizationId);
        return exchange.getBody();
    }
}
