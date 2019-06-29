package com.maslke.spring.demos.licensingservice.clients;

import com.maslke.spring.demos.licensingservice.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrganizationRestTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange = restTemplate.exchange("http://organizationservice/v1/" +
                "organizations/{organizationId}", HttpMethod.GET, null, Organization.class, organizationId);
        return restExchange.getBody();
    }
}
