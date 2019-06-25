package com.maslke.spring.demos.licensingservice.controllers;

import com.maslke.spring.demos.licensingservice.model.License;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:maslke
 * @date:2019/6/25
 * @version:0.0.1
 */
@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicense(@PathVariable String organizationId, @PathVariable String licenseId) {
        return new License().withId(licenseId)
                .withLicenseType("Seat")
                .withProductName("aaa")
                .withOrganizationId(organizationId);
    }
}
