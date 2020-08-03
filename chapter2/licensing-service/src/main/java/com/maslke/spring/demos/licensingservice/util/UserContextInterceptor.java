package com.maslke.spring.demos.licensingservice.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    @Override // intercept()方法在RestTemplate发生实际的HTTP服务调用之前被调用
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        // 在使用RestTemplate调用请求之前，会将相关的headers添加到request中。然后，比如license服务和organization服务之类的就会获取到此header的信息
        HttpHeaders headers = httpRequest.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
        headers.add(UserContext.AUTHORIZATION, UserContextHolder.getContext().getAuthorization());
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
