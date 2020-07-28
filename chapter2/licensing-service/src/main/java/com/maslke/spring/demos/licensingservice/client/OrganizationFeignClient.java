package com.maslke.spring.demos.licensingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.maslke.spring.demos.licensingservice.model.Organization;

@FeignClient("organizationService")
public interface OrganizationFeignClient {

    @GetMapping("/v1/organizations/{organizationId}")
    Organization getOrganizationById(@PathVariable String organizationId);
}
