package com.maslke.spring.demos.licensingservice.service;

import com.maslke.spring.demos.licensingservice.client.OrganizationDiscoveryClient;
import com.maslke.spring.demos.licensingservice.client.OrganizationFeignClient;
import com.maslke.spring.demos.licensingservice.client.OrganizationRestClient;
import com.maslke.spring.demos.licensingservice.config.ServiceConfig;
import com.maslke.spring.demos.licensingservice.model.License;
import com.maslke.spring.demos.licensingservice.model.Organization;
import com.maslke.spring.demos.licensingservice.repository.LicenseRepository;
import com.maslke.spring.demos.licensingservice.util.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class LicenseService {

    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

    private LicenseRepository licenseRepository;
    private ServiceConfig serviceConfig;

    private OrganizationDiscoveryClient discoveryClient;
    private OrganizationRestClient restClient;
    private OrganizationFeignClient feignClient;

    private final Random random = new Random();


    @Autowired
    public LicenseService(LicenseRepository repository, ServiceConfig serviceConfig,
                          OrganizationDiscoveryClient discoveryClient, OrganizationRestClient restClient,
                          OrganizationFeignClient feignClient) {
        this.licenseRepository = repository;
        this.serviceConfig = serviceConfig;
        this.discoveryClient = discoveryClient;
        this.restClient = restClient;
        this.feignClient = feignClient;
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization organization = retrieveOrganizatonInfo(organizationId, clientType);

        return license.withOrganizationName(organization.getName())
                .withContactName(organization.getContactName())
                .withContatctEmail(organization.getContactEmail());
    }

    private Organization retrieveOrganizatonInfo(String organizationId, String clientType) {
        if ("discoveryclient".equals(clientType)) {
            return this.discoveryClient.getOrganization(organizationId);
        }
        else if ("rest".equals(clientType)) {
            return this.restClient.getOrganization(organizationId);
        }
        else if ("feign".equals(clientType)) {
            return this.feignClient.getOrganizationById(organizationId);
        }
        else {
            return new Organization();
        }
    }

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(serviceConfig.getExampleProperty());
    }

    @HystrixCommand(threadPoolKey = "xxxKey", threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "30"),
            @HystrixProperty(name = "maxQueueSize", value = "10")}, fallbackMethod = "buildFallbackLicenseList",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "12000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")})
    public List<License> getLicensesByOrganization(String organizationId) {
        // 获取不到Correlation id,因为隔离在其他的线程组中
        logger.info("LicenseService Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        randomRunLongTime();
        return licenseRepository.findLicenseByOrganizationId(organizationId);
    }

    private void randomRunLongTime() {
        int ran = random.nextInt(10);
        if (ran <= 5) {
            try {
                Thread.sleep(15000);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    // fallback方法需要与原始的方法具有相同的参数列表。
    // getLicensesByOrganization
    public List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        fallbackList.add(new License().withId("000000-000-0000").withOrganizationId(organizationId)
                .withProductName("Sorry no licensing information currently available"));
        return fallbackList;
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

}
