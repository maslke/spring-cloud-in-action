package com.maslke.spring.demos.licensingservice.clients;

import com.maslke.spring.demos.licensingservice.model.Organization;
import com.maslke.spring.demos.licensingservice.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

/**
 * @author:maslke
 * @date:2019/7/15
 * @version:0.0.1
 */
@Component
public class OAuth2RestTemplateClient {

    @Autowired(required = false)
    OAuth2RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

    public Organization getOrganization(String organizationId) {
        logger.info("In Licensing Service.getOrganization:{}", UserContextHolder.getContext().getCorrelationId());

        ResponseEntity<Organization> restExchange = restTemplate.exchange("http://zuulservice:5555/api/organization/" +
                "v1/organizations/{organizationId}", HttpMethod.GET, null, Organization.class, organizationId);
        return restExchange.getBody();
    }
}
