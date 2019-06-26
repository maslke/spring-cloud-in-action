package com.maslke.spring.demos.licensingservice.controllers;

import com.maslke.spring.demos.licensingservice.model.License;
import com.maslke.spring.demos.licensingservice.model.ServiceConfig;
import com.maslke.spring.demos.licensingservice.service.LicenseService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LicenseService licenseService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        return licenseService.getLicenses(organizationId);
    }

    @RequestMapping(value = "{licenseId}", method = RequestMethod.PUT)
    public String updateLicenses(@PathVariable String licenseId) {
        return String.format("This is the put");
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicense(@PathVariable String organizationId, @PathVariable String licenseId) {
        return licenseService.getLicense(organizationId, licenseId);
    }
}
