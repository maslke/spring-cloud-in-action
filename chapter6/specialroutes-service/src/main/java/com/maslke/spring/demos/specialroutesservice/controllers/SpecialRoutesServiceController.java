package com.maslke.spring.demos.specialroutesservice.controllers;

import com.maslke.spring.demos.specialroutesservice.model.AbTestingRoute;
import com.maslke.spring.demos.specialroutesservice.services.AbTestingRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maslke
 */
@RestController
@RequestMapping("/v1/route")
public class SpecialRoutesServiceController {

    private AbTestingRouteService routeService;

    @Autowired
    public SpecialRoutesServiceController(AbTestingRouteService service) {
        this.routeService = service;
    }

    @GetMapping(value = "/abtesting/{serviceName}")
    public AbTestingRoute abtestings(@PathVariable String serviceName) {
        return routeService.getRoute(serviceName);
    }

    @PostMapping(value = "/abtesting/{serviceName}")
    public void save(@PathVariable String serviceName, @RequestBody AbTestingRoute route) {
        route.setServiceName(serviceName);
        routeService.saveAbTestingRoute(route);
    }
}
