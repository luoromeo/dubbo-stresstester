package com.luoromeo.stresstester.entity;

public class ApiEntity {

    private String name;

    private String apiVersion;

    private String dubboApiName;

    private String dubboApiVersion;

    public ApiEntity() {
    }

    public ApiEntity(String name, String apiVersion, String dubboApiName) {
        this.name = name;
        this.apiVersion = apiVersion;
        this.dubboApiName = dubboApiName;
    }

    public ApiEntity(String name, String apiVersion, String dubboApiName, String dubboApiVersion) {
        this.name = name;
        this.apiVersion = apiVersion;
        this.dubboApiName = dubboApiName;
        this.dubboApiVersion = dubboApiVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getDubboApiName() {
        return dubboApiName;
    }

    public void setDubboApiName(String dubboApiName) {
        this.dubboApiName = dubboApiName;
    }

    public String getDubboApiVersion() {
        return dubboApiVersion;
    }

    public void setDubboApiVersion(String dubboApiVersion) {
        this.dubboApiVersion = dubboApiVersion;
    }
}
