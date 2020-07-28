package com.maslke.spring.demos.licensingservice.service;

import com.maslke.spring.demos.licensingservice.client.OrganizationDiscoveryClient;
import com.maslke.spring.demos.licensingservice.client.OrganizationFeignClient;
import com.maslke.spring.demos.licensingservice.client.OrganizationRestClient;
import com.maslke.spring.demos.licensingservice.config.ServiceConfig;
import com.maslke.spring.demos.licensingservice.model.License;
import com.maslke.spring.demos.licensingservice.model.Organization;
import com.maslke.spring.demos.licensingservice.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {
    private LicenseRepository licenseRepository;
    private ServiceConfig serviceConfig;

    private OrganizationDiscoveryClient discoveryClient;
    private OrganizationRestClient restClient;
    private OrganizationFeignClient feignClient;


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
        } else if ("rest".equals(clientType)) {
            return this.restClient.getOrganization(organizationId);
        } else if ("feign".equals(clientType)){
            return this.feignClient.getOrganizationById(organizationId);
        } else {
            return new Organization();
        }
    }

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(serviceConfig.getExampleProperty());
    }

    public List<License> getLicensesByOrganization(String organizationId) {
        return licenseRepository.findLicenseByOrganizationId(organizationId);
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

}
