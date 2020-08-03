package com.maslke.spring.demos.zuulsvr.util;

import com.netflix.zuul.context.RequestContext;
import org.apache.http.auth.AUTH;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Component
public class FilterUtils {

    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    public static final String TMX_CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTHORIZATION = "Authorization";

    public String getServiceId() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        if (requestContext.get("serviceId") == null) {
            return "";
        }
        return requestContext.get("serviceId").toString();
    }

    public String getCorrelationId() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        // RequestContext是Zuul RequestContext
        // 1 从request中获取header
        // 2 调用context.getZuulRequestHeaders()来获取header
        if (request.getHeader(TMX_CORRELATION_ID) != null) {
            return request.getHeader(TMX_CORRELATION_ID);
        } else {
            return context.getZuulRequestHeaders().get(TMX_CORRELATION_ID);
        }
    }

    public void setCorrelationId(String generateCorrelationId) {
        RequestContext context = RequestContext.getCurrentContext();
        // 将关联ID添加到Zuul的Request Header中。
        context.addZuulRequestHeader(TMX_CORRELATION_ID, generateCorrelationId);
    }

    public void setAuthorization(String authorization) {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.addZuulRequestHeader(AUTHORIZATION, authorization);
    }

    public String getAuthorization() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (request.getHeader(AUTHORIZATION) != null) {
            return request.getHeader(AUTHORIZATION);
        }
        return requestContext.getZuulRequestHeaders().get(AUTHORIZATION);
    }
}
