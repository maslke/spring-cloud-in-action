package com.maslke.spring.demos.specialroutesservice.services;

import com.maslke.spring.demos.specialroutesservice.exceptions.RouteNotFound;
import com.maslke.spring.demos.specialroutesservice.model.AbTestingRoute;
import com.maslke.spring.demos.specialroutesservice.repository.AbTestingRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author maslke
 */
@Service
public class AbTestingRouteService {

    private AbTestingRouteRepository repository;

    @Autowired
    public AbTestingRouteService(AbTestingRouteRepository repository) {
        this.repository = repository;
    }

    public AbTestingRoute getRoute(String serviceName) {
        AbTestingRoute route = repository.findByServiceName(serviceName);
        if (route == null) {
            throw new RouteNotFound();
        }
        return route;
    }

    public void saveAbTestingRoute(AbTestingRoute route) {
        repository.save(route);
    }

    public void updateAbTestingRoute(AbTestingRoute route) {
        repository.save(route);
    }

    public void deleteRoute(AbTestingRoute route) {
        repository.delete(route);
    }

}
