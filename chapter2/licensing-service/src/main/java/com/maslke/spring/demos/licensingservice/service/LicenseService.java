package com.maslke.spring.demos.licensingservice.service;

import com.maslke.spring.demos.licensingservice.clients.OAuth2RestTemplateClient;
import com.maslke.spring.demos.licensingservice.clients.OrganizationDiscoveryClient;
import com.maslke.spring.demos.licensingservice.clients.OrganizationFeignClient;
import com.maslke.spring.demos.licensingservice.clients.OrganizationRestTemplateClient;
import com.maslke.spring.demos.licensingservice.model.License;
import com.maslke.spring.demos.licensingservice.model.Organization;
import com.maslke.spring.demos.licensingservice.model.ServiceConfig;
import com.maslke.spring.demos.licensingservice.repository.LicenseRepository;
import com.maslke.spring.demos.licensingservice.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:maslke
 * @date:2019/6/26
 * @version:0.0.1
 */
@Service
public class LicenseService {

    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired(required = false)
    private OrganizationFeignClient organizationFeignClient;


    @Autowired(required = false)
    private OrganizationDiscoveryClient discoveryClient;

    @Autowired(required = false)
    private OrganizationRestTemplateClient restTemplateClient;

    @Autowired(required = false)
    private OAuth2RestTemplateClient oAuth2RestTemplateClient;

    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization organization = null;
        logger.info("reteieveOrgInfo:{}", organizationId);
        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = restTemplateClient.getOrganization(organizationId);
                break;
            case "discovery":
                organization = discoveryClient.getOrganization(organizationId);
                System.out.println("I am using the discovery client");
                break;
            case "oauth2":
                organization = oAuth2RestTemplateClient.getOrganization(organizationId);
                System.out.println("I am using the oauth2 client");
            default:
                break;
        }
        return organization;
    }

    private void randomlyRunLong() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3) {
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(30000);
        }
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License().withLicenseId("000000-00-00000")
                .withOrganizationId(organizationId)
                .withProductName("sorry no licensing information currentyly available");
        fallbackList.add(license);
        return fallbackList;
    }

    @HystrixCommand(threadPoolKey = "getLicensesByOrgThreadPool",
            threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "30"), @HystrixProperty(name = "maxQueueSize", value = "10")},
            fallbackMethod = "buildFallbackLicenseList",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")})
    public List<License> getLicenses(String organizationId) {
        randomlyRunLong();
        logger.debug("LicenseService Correlation id : {}", UserContextHolder.getContext().getCorrelationId());
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.withComment(serviceConfig.getExampleProperty());
        return license;
    }

    public License save(License license) {
        license.withLicenseId(UUID.randomUUID().toString());
        return licenseRepository.save(license);
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        logger.info("licensing service: getLiense");
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license;
//        Organization organization = retrieveOrgInfo(organizationId, clientType);
//        return license.withOrganizationName(organization.getName())
//                .withContactEmail(organization.getContactEmail())
//                .withContactName(organization.getContactName())
//                .withContactPhone(organization.getContactPhone());

    }


}
