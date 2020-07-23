package com.maslke.spring.demos.licensingservice.service;

import com.maslke.spring.demos.licensingservice.config.ServiceConfig;
import com.maslke.spring.demos.licensingservice.model.License;
import com.maslke.spring.demos.licensingservice.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {
    private LicenseRepository licenseRepository;
    private ServiceConfig serviceConfig;


    @Autowired
    public LicenseService(LicenseRepository repository, ServiceConfig serviceConfig) {
        this.licenseRepository = repository;
        this.serviceConfig = serviceConfig;
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
