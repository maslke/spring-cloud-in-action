package com.maslke.spring.demos.licensingservice.service;

import com.maslke.spring.demos.licensingservice.clients.OrganizationFeignClient;
import com.maslke.spring.demos.licensingservice.model.License;
import com.maslke.spring.demos.licensingservice.model.Organization;
import com.maslke.spring.demos.licensingservice.model.ServiceConfig;
import com.maslke.spring.demos.licensingservice.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author:maslke
 * @date:2019/6/26
 * @version:0.0.1
 */
@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization organization = null;
        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
        }
        return organization;
    }

    public List<License> getLicenses(String organizationId) {
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
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        Organization organization = retrieveOrgInfo(organizationId, clientType);
        return license.withOrganizationName(organization.getName())
                .withContactEmail(organization.getContactEmail())
                .withContactName(organization.getContactName())
                .withContactPhone(organization.getContactPhone());

    }


}
