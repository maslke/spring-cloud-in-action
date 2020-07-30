package com.maslke.spring.demos.zuulsvr.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class TrackingFilter extends ZuulFilter { // 覆盖ZuulFilter的某些方法

    private static final int FILTER_ORDER = 1;

    private static final boolean SHOULD_FILTER = true;

    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    private FilterUtils filterUtils;

    @Autowired
    public TrackingFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent() {
        return filterUtils.getCorrelationId() != null;
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Object run() throws ZuulException {
        if (isCorrelationIdPresent()) {
            logger.debug("tmx-correlation-id found in tracking filter: {}", filterUtils.getCorrelationId());
        } else {
            filterUtils.setCorrelationId(generateCorrelationId());
            logger.debug("tmx-correlation-id generated in tracking filter:{}", filterUtils.getCorrelationId());
        }
        // zuul Request context
        RequestContext context = RequestContext.getCurrentContext();
        logger.debug("Processing incoming request for {}", context.getRequest().getRequestURI());
        return null;
    }
}
