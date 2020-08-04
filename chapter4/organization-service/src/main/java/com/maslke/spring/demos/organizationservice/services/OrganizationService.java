package com.maslke.spring.demos.organizationservice.services;

import com.maslke.spring.demos.organizationservice.event.source.SimpleSourceBean;
import com.maslke.spring.demos.organizationservice.model.Organization;
import com.maslke.spring.demos.organizationservice.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;

    private SimpleSourceBean simpleSourceBean;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, SimpleSourceBean simpleSourceBean) {
        this.organizationRepository = organizationRepository;
        this.simpleSourceBean = simpleSourceBean;
    }

    public Organization getOrg(String organizationId) {
        return organizationRepository.findByOrganizationId(organizationId);
    }

    public void saveOrg(Organization org) {
        org.setOrganizationId(UUID.randomUUID().toString());
        organizationRepository.save(org);
        this.simpleSourceBean.publishOrgChange("SAVE", org.getOrganizationId());
    }

    public void updateOrg(Organization org) {
        organizationRepository.save(org);
        this.simpleSourceBean.publishOrgChange("UPDATE", org.getOrganizationId());
    }

    public void deleteOrg(Organization org) {
        organizationRepository.deleteById(org.getOrganizationId());
        this.simpleSourceBean.publishOrgChange("DELETE", org.getOrganizationId());
    }
}
