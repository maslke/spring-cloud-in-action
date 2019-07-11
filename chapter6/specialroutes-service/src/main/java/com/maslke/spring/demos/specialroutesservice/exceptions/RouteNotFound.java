package com.maslke.spring.demos.specialroutesservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author:maslke
 * @date:2019/7/9
 * @version:0.0.1
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RouteNotFound extends RuntimeException {
}
