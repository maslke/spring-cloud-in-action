package com.maslke.spring.demos.licensingservice.controller;

import com.maslke.spring.demos.licensingservice.model.License;
import com.maslke.spring.demos.licensingservice.service.LicenseService;
import com.maslke.spring.demos.licensingservice.util.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

    private LicenseService licenseService;

    @Autowired
    public LicenseServiceController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }


    @GetMapping("/")
    public List<License> getLicenses(@PathVariable String organizationId) {
        logger.info("LicenserviceController Corelation id: {}", UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicensesByOrganization(organizationId);
    }


    @GetMapping("/{licenseId}")
    public License getLicense(@PathVariable String organizationId, @PathVariable String licenseId) {
        logger.info("LicenserviceController Coleration id: {}", UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicense(organizationId, licenseId);
    }

    @GetMapping("/{licenseId}/{clientType}")
    public License getLicensesWithClient(@PathVariable String organizationId, @PathVariable String licenseId, @PathVariable String clientType) {
        logger.info("LicenserviceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicense(organizationId, licenseId, clientType);
    }
}
