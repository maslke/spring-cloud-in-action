package com.maslke.spring.demos.licensingservice.service;

import com.maslke.spring.demos.licensingservice.model.License;
import com.maslke.spring.demos.licensingservice.model.ServiceConfig;
import com.maslke.spring.demos.licensingservice.repository.LicenseRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<License> getLicenses(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.withComment(serviceConfig.getExampleProperty());
        return license;
    }

    public void save(License license) {
        license.withLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }
}
