package com.maslke.spring.demos.licensingservice.repository;

import com.maslke.spring.demos.licensingservice.model.License;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:maslke
 * @date:2019/6/26
 * @version:0.0.1
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
    List<License> findByOrganizationId(String organizationId);

    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
