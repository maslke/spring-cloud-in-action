package com.maslke.spring.demos.specialroutesservice.controllers;

import com.maslke.spring.demos.specialroutesservice.model.AbTestingRoute;
import com.maslke.spring.demos.specialroutesservice.services.AbTestingRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:maslke
 * @date:2019/7/9
 * @version:0.0.1
 */
@RestController
@RequestMapping("/v1/route")
public class SpecialRoutesServiceController {
    @Autowired
    AbTestingRouteService routeService;

    @RequestMapping(value = "/abtesting/{serviceName}", method = RequestMethod.GET)
    public AbTestingRoute abtestings(@PathVariable String serviceName) {
        return routeService.getRoute(serviceName);
    }

    @RequestMapping(value = "/abtesting/{serviceName}", method = RequestMethod.POST)
    public void save(@PathVariable String serviceName, @RequestBody AbTestingRoute route) {
        route.setServiceName(serviceName);
        routeService.saveAbTestingRoute(route);
    }
}
