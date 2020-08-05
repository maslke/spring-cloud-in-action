package com.maslke.spring.demos.licensingservice.repository;

import com.maslke.spring.demos.licensingservice.model.Organization;

public interface OrganizationRedisRepository {
    void saveOrganization(Organization organization);
    void updateOrganization(Organization organization);
    void deleteOrganization(Organization organization);
    Organization findOrganization(String organizationId);
}
