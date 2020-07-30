package com.maslke.spring.demos.zuulsvr.filter;

// UserContext\UserContextFilter\UserContextHolder三个类和在licensingservice类中的代码基本一致。
// 在微服务结构中，尽量避免代码层面的共享。虽然是可以通过增加一层依赖来进行处理，但在微服务架构中一般不采用这种方式。
public class UserContext {

    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String USER_ID = "tmx-user-id";
    public static final String AUTH_TOKEN = "tmx-auth-token";
    public static final String ORG_ID = "tmx-org-id";

    private String correlationId;

    private String userId;

    private String authToken;

    private String orgId;

    public UserContext() {
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
