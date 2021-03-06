package com.maslke.spring.demos.organizationservice.controller;

import com.maslke.spring.demos.organizationservice.model.Organization;
import com.maslke.spring.demos.organizationservice.services.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/organizations")
public class OrganizationServiceController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceController.class);

    private OrganizationService organizationService;

    @Autowired
    public OrganizationServiceController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping(value = "/{organizationId}")
    public Organization getOrganization(@PathVariable String organizationId) {
        logger.info("Looking up data for org {}", organizationId);
        Organization org = organizationService.getOrg(organizationId);
        org.setContactName("NEW::" + org.getContactName());
        return org;
    }

    @PutMapping(value = "/{organizationId}")
    public void updateOrg(@PathVariable String organizationId, @RequestBody Organization organization) {
        organization.setOrganizationId(organizationId);
        organizationService.updateOrg(organization);
    }

    @PostMapping(value = "/{organizationId}")
    public void saveOrganization(@RequestBody Organization organization) {
        organizationService.saveOrg(organization);
    }

    @DeleteMapping(value = "/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable String organizationId, @RequestBody Organization org) {
        org.setOrganizationId(organizationId);
        organizationService.deleteOrg(org);
    }
}
