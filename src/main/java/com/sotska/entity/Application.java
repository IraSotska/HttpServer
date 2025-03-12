package com.sotska.entity;

import jakarta.servlet.http.HttpServlet;

import java.util.Map;

public class Application {
    private String appName;
    private Map<String, HttpServlet> urlServletMap;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Map<String, HttpServlet> getUrlServletMap() {
        return urlServletMap;
    }

    public void setUrlServletMap(Map<String, HttpServlet> urlServletMap) {
        this.urlServletMap = urlServletMap;
    }
}
