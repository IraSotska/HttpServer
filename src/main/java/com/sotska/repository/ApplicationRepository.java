package com.sotska.repository;

import com.sotska.entity.Application;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationRepository {
    private final Map<String, Application> pathApplicationMap = new ConcurrentHashMap<>();

    public void add(String appName, Application application) {
        pathApplicationMap.put(appName, application);
    }

    public void remove(String appName) {
        pathApplicationMap.remove(appName);
    }

    public Optional<Application> get(String appName) {
        if (pathApplicationMap.containsKey(appName)) {
            return Optional.of(pathApplicationMap.get(appName));
        }
        return Optional.empty();
    }
}
