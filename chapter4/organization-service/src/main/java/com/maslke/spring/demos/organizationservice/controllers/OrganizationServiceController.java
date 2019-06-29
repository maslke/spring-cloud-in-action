package com.maslke.spring.demos.organizationservice.controllers;

import com.maslke.spring.demos.organizationservice.model.Organization;
import com.maslke.spring.demos.organizationservice.services.OrganizationService;
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
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable String organizationId) {
        return organizationService.getOrg(organizationId);
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
