package com.maslke.spring.demos.zuulsvr.filter;

import com.maslke.spring.demos.zuulsvr.util.FilterUtils;
import com.maslke.spring.demos.zuulsvr.util.UserContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    private FilterUtils filterUtils;

    @Autowired
    public ResponseFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        logger.info("Adding the correlation id to the response headers. {}", filterUtils.getCorrelationId());
        requestContext.getResponse().addHeader(UserContext.CORRELATION_ID, filterUtils.getCorrelationId());
        logger.info("Completing outgoing request for {}.", requestContext.getRequest().getRequestURI());
        return null;
    }

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }
}
