package com.maslke.spring.demos.licensingservice.client;

import com.maslke.spring.demos.licensingservice.model.Organization;
import com.maslke.spring.demos.licensingservice.repository.OrganizationRedisRepositoryImpl;
import com.maslke.spring.demos.licensingservice.util.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRedisClient {

    private RestTemplate restTemplate;

    private OrganizationRedisRepositoryImpl organizationRedisRepository;

    @Autowired
    public OrganizationRedisClient(RestTemplate restTemplate, OrganizationRedisRepositoryImpl organizationRedisRepository) {
        this.restTemplate = restTemplate;
        this.organizationRedisRepository = organizationRedisRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRedisClient.class);

    public Organization getOrganization(String organizationId) {
        logger.info("In Licensing Service.getOrganization:{}", UserContextHolder.getContext().getCorrelationId());
        Organization organization = checkRedisCache(organizationId);
        if (organization == null) {
            logger.info("Retrieve no organization data from redis cache:{}", organizationId);
            ResponseEntity<Organization> responseEntity = restTemplate.exchange("http://organizationservice/v1/organizations/{organizationId}",
                    HttpMethod.GET, null, Organization.class, organizationId);
            organization = responseEntity.getBody();
            if (organization != null) {
                cacheOrganization(organization);
            }
        }
        else {
            logger.info("Retrieve Organization from redis cache:{}", organizationId);
        }

        return organization;
    }

    private Organization checkRedisCache(String organizationId) {
        try {
            return this.organizationRedisRepository.findOrganization(organizationId);
        }
        catch (Exception ex) {
            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.Exception {}",
                    organizationId, ex);
        }
        return null;
    }

    private void cacheOrganization(Organization org) {
        try {
            this.organizationRedisRepository.saveOrganization(org);
        }
        catch (Exception ex) {
            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.Exception {}",
                    org.getOrganizationId(), ex);
        }
    }
}
