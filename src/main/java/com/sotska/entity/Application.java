package com.sotska.entity;

import javax.servlet.http.HttpServlet;
import java.util.Map;

public class Application {
    private String appName;
    private Map<String, HttpServlet> pathServletMap;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Map<String, HttpServlet> getPathServletMap() {
        return pathServletMap;
    }

    public void setPathServletMap(Map<String, HttpServlet> pathServletMap) {
        this.pathServletMap = pathServletMap;
    }
}
