package com.maslke.spring.demos.licensingservice.repository;

import com.maslke.spring.demos.licensingservice.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
    List<License> findLicenseByOrganizationId(String organizationId);
    License findByOrOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
