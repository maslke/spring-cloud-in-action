package com.maslke.spring.demos.orgservicenew.services;

import com.maslke.spring.demos.orgservicenew.model.Organization;
import com.maslke.spring.demos.orgservicenew.repository.OrganizationRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

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
