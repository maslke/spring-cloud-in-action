package com.maslke.spring.demos.organizationservice.services;

import com.maslke.spring.demos.organizationservice.model.Organization;
import com.maslke.spring.demos.organizationservice.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization getOrg(String organizationId) {
        return organizationRepository.findByOrganizationId(organizationId);
    }

    public void saveOrg(Organization org) {
        org.setOrganizationId(UUID.randomUUID().toString());
        organizationRepository.save(org);
    }

    public void updateOrg(Organization org) {
        organizationRepository.save(org);
    }

    public void deleteOrg(Organization org) {
        organizationRepository.deleteById(org.getOrganizationId());
    }
}
