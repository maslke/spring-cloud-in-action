package com.maslke.spring.demos.zuulsvr.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class FilterUtils {

    public static final String PRE_FILTER_TYPE = "";

    public static final String TMX_CORRELATION_ID = "tmx-correlation-id";

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
}
