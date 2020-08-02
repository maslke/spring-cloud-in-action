package com.maslke.spring.demos.organizationservice.repository;

import com.maslke.spring.demos.organizationservice.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
    Organization findByOrganizationId(String organizationId);
}
