package com.maslke.spring.demos.orgservicenew.controllers;

import com.maslke.spring.demos.orgservicenew.services.OrganizationService;
import com.maslke.spring.demos.orgservicenew.model.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/organizations")
public class OrganizationServiceController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceController.class);
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable String organizationId) {
        logger.info("Looking up data for org {}", organizationId);
        Organization org = organizationService.getOrg(organizationId);
        org.setContactName("NEW::" + org.getContactName());
        return org;
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.PUT)
    public void updateOrg(@PathVariable String organizationId, @RequestBody Organization organization) {
        organization.setOrganizationId(organizationId);
        organizationService.updateOrg(organization);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization organization) {
        organizationService.saveOrg(organization);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable String orgId, @RequestBody Organization org) {
        org.setOrganizationId(orgId);
        organizationService.deleteOrg(org);
    }
}
