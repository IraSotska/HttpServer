package com.sotska.entity;

import java.util.Map;

public class ApplicationSettings {
    private String name;

    private Map<String, String> servletNameToUrlMap;

    public ApplicationSettings(String name, Map<String, String> servletNameToUrlMap) {
        this.name = name;
        this.servletNameToUrlMap = servletNameToUrlMap;
    }

    public ApplicationSettings() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getServletNameToUrlMap() {
        return servletNameToUrlMap;
    }

    public void setServletNameToUrlMap(Map<String, String> urlServletPathMap) {
        this.servletNameToUrlMap = urlServletPathMap;
    }
}
