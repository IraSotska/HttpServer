package com.sotska.entity;

import java.util.Map;

public class ApplicationSettings {
    private String name;

    private Map<String, String> urlServletPathMap;

    public ApplicationSettings(String name, Map<String, String> urlServletPathMap) {
        this.name = name;
        this.urlServletPathMap = urlServletPathMap;
    }

    public ApplicationSettings() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getUrlServletPathMap() {
        return urlServletPathMap;
    }

    public void setUrlServletPathMap(Map<String, String> urlServletPathMap) {
        this.urlServletPathMap = urlServletPathMap;
    }
}
