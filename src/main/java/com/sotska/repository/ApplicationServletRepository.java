package com.sotska.repository;

import com.sotska.entity.Application;

import javax.servlet.http.HttpServlet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationServletRepository {

    private final Map<String, Application> pathApplicationMap = new ConcurrentHashMap<>();

    public void add(String appName, Application application) {
        pathApplicationMap.put(appName, application);
    }

    public void remove(String appName) {
        pathApplicationMap.remove(appName);
    }

    public HttpServlet get(String appName, String path) throws IllegalArgumentException {
        if (pathApplicationMap.containsKey(appName) && pathApplicationMap.get(appName).getPathServletMap().containsKey(path)) {
            return pathApplicationMap.get(appName).getPathServletMap().get(path);
        }
        throw new IllegalArgumentException("Application: " + appName + " with path: " + path + " not found.");
    }
}
