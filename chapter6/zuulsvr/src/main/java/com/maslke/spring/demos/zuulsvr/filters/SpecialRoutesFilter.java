package com.maslke.spring.demos.zuulsvr.filters;

import com.maslke.spring.demos.zuulsvr.model.AbTestingRoute;
import com.maslke.spring.demos.zuulsvr.utils.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author:maslke
 * @date:2019/7/8
 * @version:0.0.1
 */
@Component
public class SpecialRoutesFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(SpecialRoutesFilter.class);

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    @Autowired
    FilterUtils filterUtils;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String filterType() {
        return FilterUtils.ROUTE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("serviceId====================={}", ctx.get("serviceId"));
        AbTestingRoute abTestingRoute = getAbRoutingInfo(filterUtils.getServiceId());
        logger.info("AbTestingRoute======================{}", abTestingRoute);
        if (abTestingRoute != null && useSpecialRoute(abTestingRoute)) {
            String route = buildRouteString(ctx.getRequest().getRequestURI(),
                    abTestingRoute.getEndpoint(), ctx.get("serviceId").toString());
            logger.info("route==================={}", route);
            forwardToSpecialRoute(route);
        }
        return null;
    }

    private void forwardToSpecialRoute(String route) {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        MultiValueMap<String, String> headers = this.helper.buildZuulRequestHeaders(request);
        MultiValueMap<String, String> params = this.helper.buildZuulRequestQueryParams(request);
        String verb = getVerb(request);
        InputStream requestEntity = getRequestBody(request);
        if (request.getContentLength() < 0) {
            ctx.setChunkedRequestBody();
        }

        this.helper.addIgnoredHeaders();
        CloseableHttpClient httpClient = null;
        HttpResponse response = null;

        try {
            httpClient = HttpClients.createDefault();
            response = forward(httpClient, verb, route, request, headers, params, requestEntity);
            setResponse(response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                httpClient.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private ProxyRequestHelper helper = new ProxyRequestHelper();

    private AbTestingRoute getAbRoutingInfo(String serviceName) {
        ResponseEntity<AbTestingRoute> restExchange = null;
        try {
            restExchange = restTemplate.exchange("http://specialroutesservice/v1/route/abtesting/{serviceName}",
                    HttpMethod.GET, null, AbTestingRoute.class, serviceName);
        }
        catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw ex;
        }
        return restExchange.getBody();
    }

    private String buildRouteString(String oldEndpoint, String newEndpoint, String serviceName) {
        int index = oldEndpoint.indexOf(serviceName);
        String strippedRoute = oldEndpoint.substring(index + serviceName.length());
        logger.info("Target route:{}{}", newEndpoint, strippedRoute);
        System.out.println("Target route:" + String.format("%s%s", newEndpoint, strippedRoute));
        return String.format("%s/%s", newEndpoint, strippedRoute);
    }

    private String getVerb(HttpServletRequest request) {
        String method = request.getMethod();
        return method.toUpperCase();
    }

    private HttpHost getHttpHost(URL host) {
        HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(), host.getProtocol());
        return httpHost;
    }

    private Header[] convertHeaders(MultiValueMap<String, String> headers) {
        List<Header> list = new ArrayList<>();
        for (String name : headers.keySet()) {
            for (String value : headers.get(name)) {
                list.add(new BasicHeader(name, value));
            }
        }
        Header[] headers1 = new BasicHeader[list.size()];
        for (int i = 0; i < list.size(); i++) {
            headers1[i] = list.get(i);
        }
        return headers1;
    }

    private HttpResponse forwardRequest(HttpClient httpClient, HttpHost httpHost,
                                        HttpRequest httpRequest) throws IOException {
        return httpClient.execute(httpHost, httpRequest);
    }

    private MultiValueMap<String, String> revertHeaders(Header[] headers) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (Header header : headers) {
            String name = header.getName();
            if (!map.containsKey(name)) {
                map.put(name, new ArrayList<>());
            }
            map.get(name).add(header.getValue());
        }
        return map;
    }

    private InputStream getRequestBody(HttpServletRequest request) {
        InputStream requestEntity = null;
        try {
            requestEntity = request.getInputStream();
        }
        catch (IOException ex) {
            //
        }
        return requestEntity;
    }

    private void setResponse(HttpResponse response) throws IOException {
//        this.helper.setResponse(response.getStatusLine().getStatusCode(),
//                response.getEntity() == null ? null : response.getEntity().getContent(),
//                revertHeaders(response.getAllHeaders()));
        logger.info("you are here=============================================================");
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(response.getStatusLine().getStatusCode());
        ctx.setResponseDataStream(response.getEntity().getContent());
    }

    private HttpResponse forward(HttpClient httpClient, String verb, String url,
                                 HttpServletRequest request, MultiValueMap<String, String> headers,
                                 MultiValueMap<String, String> params, InputStream requestEntity) throws Exception {
        Map<String, Object> info = this.helper.debug(verb, url, headers, params, requestEntity);
        URL host = new URL(url);
        HttpHost httpHost = getHttpHost(host);

        HttpRequest httpRequest;
        int contentLength = request.getContentLength();
        InputStreamEntity entity = new InputStreamEntity(requestEntity, contentLength,
                request.getContentType() != null ? ContentType.create(request.getContentType()) : null);
        switch (verb.toUpperCase()) {
            case "POST":
                HttpPost httpPost = new HttpPost(url);
                httpRequest = httpPost;
                httpPost.setEntity(entity);
                break;
            case "PUT":
                HttpPut httpPut = new HttpPut(url);
                httpRequest = httpPut;
                httpPut.setEntity(entity);
                break;
            case "PATCH":
                HttpPatch httpPatch = new HttpPatch(url);
                httpRequest = httpPatch;
                httpPatch.setEntity(entity);
                break;
            default:
                httpRequest = new BasicHttpRequest(verb, url);
        }
        try {
            httpRequest.setHeaders(convertHeaders(headers));
            return forwardRequest(httpClient, httpHost, httpRequest);
        }
        finally {

        }
    }

    public boolean useSpecialRoute(AbTestingRoute testingRoute) {
        Random random = new Random();
        if (testingRoute.getActive().equals("N")) {
            return false;
        }

        int value = random.nextInt((10 - 1) + 1) + 1;
        logger.info("value========================={}", value);
        return testingRoute.getWeight() < value;
    }

}