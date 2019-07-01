package com.maslke.spring.demos.licensingservice.controllers;

import com.maslke.spring.demos.licensingservice.model.License;
import com.maslke.spring.demos.licensingservice.service.LicenseService;
import com.maslke.spring.demos.licensingservice.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:maslke
 * @date:2019/6/25
 * @version:0.0.1
 */
@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    private final static Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/{licenseId}/{clientType}")
    public License getLicenseWithClient(@PathVariable String organizationId, @PathVariable String licenseId, @PathVariable String clientType) {
        return licenseService.getLicense(organizationId, licenseId, clientType);
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        logger.debug("LicenseServiceController Correlation id : {}", UserContextHolder.getContext().getCorrelationId());
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public License addLicense(@RequestBody License license, @PathVariable String organizationId) {
        license.setOrganizationId(organizationId);
        return licenseService.save(license);
    }
}
