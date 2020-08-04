package com.maslke.spring.demos.organizationservice.util;

public class UserContext {
    // 根据zuulservice的那一章节中的设定
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String USER_ID = "tmx-user-id";
    public static final String AUTH_TOKEN = "tmx-auth-token";
    public static final String ORG_ID = "tmx-org-id";
    public static final String AUTHORIZATION = "Authorization";

    private String correlationId;

    private String userId;

    private String authToken;

    private String orgId;

    private String authorization;

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

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
