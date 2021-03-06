package com.maslke.spring.demos.organizationservice.model;

public class OrganizationChangeModel {
    private String typeName;
    private String actionName;
    private String organizationId;
    private String correlationId;


    public OrganizationChangeModel(String typeName, String actionName, String organizationId, String correlationId) {
        this.typeName = typeName;
        this.actionName = actionName;
        this.organizationId = organizationId;
        this.correlationId = correlationId;
    }

    public OrganizationChangeModel(String actionName, String organizationId, String correlationId) {
        this(OrganizationChangeModel.class.getTypeName(), actionName, organizationId, correlationId);
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
