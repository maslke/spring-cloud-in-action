package com.maslke.spring.demos.specialroutesservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author maslke
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RouteNotFound extends RuntimeException {
}
