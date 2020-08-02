package com.maslke.spring.demos.specialroutesservice.repository;

import com.maslke.spring.demos.specialroutesservice.model.AbTestingRoute;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author maslke
 */
@Repository
public interface AbTestingRouteRepository extends JpaRepository<AbTestingRoute, String> {
    AbTestingRoute findByServiceName(String serviceName);
}
