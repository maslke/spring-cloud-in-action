package com.maslke.spring.demos.licensingservice.clients;

import com.maslke.spring.demos.licensingservice.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("organizationservice")
@Service
public interface OrganizationFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/v1/organizations/{organizationId}", consumes = "application/json")
    Organization getOrganization(@PathVariable String organizationId);
}
