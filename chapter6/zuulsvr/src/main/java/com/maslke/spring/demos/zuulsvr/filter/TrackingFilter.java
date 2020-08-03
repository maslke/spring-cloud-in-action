package com.maslke.spring.demos.zuulsvr.filter;

import com.maslke.spring.demos.zuulsvr.config.ServiceConfig;
import com.maslke.spring.demos.zuulsvr.util.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class TrackingFilter extends ZuulFilter { // 覆盖ZuulFilter的某些方法

    private static final int FILTER_ORDER = 1;

    private static final boolean SHOULD_FILTER = true;

    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    private FilterUtils filterUtils;

    private ServiceConfig serviceConfig;

    @Autowired
    public TrackingFilter(FilterUtils filterUtils, ServiceConfig serviceConfig) {
        this.filterUtils = filterUtils;
        this.serviceConfig = serviceConfig;
    }

    // 关于filter_type
    // 并不能随意的定义，在zuul中定义了四种，分别为pre\post\route\error
    // 如果自行定义了filter type，且不在以上四种类型之内的，则ZuulFilter并不会注入到请求的调用中
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
        logger.info("ORGANIZATIONID:{}", getOrganizationId());
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

    private String getOrganizationId() {
        String orgId = "";
        if (filterUtils.getAuthorization() != null) {
            String authorization = filterUtils.getAuthorization().replace("Bearer", "");
            try {
                Claims claims = Jwts.parser().setSigningKey(serviceConfig.getJwtSigningKey().getBytes(StandardCharsets.UTF_8))
                        .parseClaimsJws(authorization)
                        .getBody();
                orgId = claims.get("organizationId").toString();
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return orgId;
    }
}
