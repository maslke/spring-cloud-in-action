package com.maslke.spring.demos.zuulsvr.model;

/**
 * @author:maslke
 * @date:2019/7/8
 * @version:0.0.1
 */
public class UserInfo {
    String organizationId;
    String userId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
