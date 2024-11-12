package com.sotska.creator;

import com.sotska.loader.ServletClassLoaderService;
import com.sotska.entity.Application;
import com.sotska.entity.ApplicationSettings;

import javax.servlet.http.HttpServlet;
import java.lang.reflect.Constructor;
import java.nio.file.FileSystems;
import java.util.Map;

import static com.sotska.service.ApplicationListener.APPS_PATH;
import static java.util.stream.Collectors.toMap;

public class ApplicationCreator {

    private static final String SEPARATOR = FileSystems.getDefault().getSeparator();

    public Application create(String applicationPath, ApplicationSettings applicationSettings) {
        Map<String, HttpServlet> pathServletMap = applicationSettings.getUrlServletPathMap().entrySet().stream()
                .collect(toMap(Map.Entry::getValue, entry -> createInstance(applicationPath, entry.getKey()
                        .replace(SEPARATOR, "."))));

        Application application = new Application();
        application.setPathServletMap(pathServletMap);
        application.setAppName(applicationPath.substring((APPS_PATH + SEPARATOR).length()));

        return application;
    }

    private HttpServlet createInstance(String appPath, String servletPath) {
        try {
            ClassLoader classLoader = new ServletClassLoaderService().getClassLoader(appPath);
            Class<?> servletClass = classLoader.loadClass(servletPath);

            Constructor<?> constructor = servletClass.getConstructor();

            return (HttpServlet) constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can't create instance by path: " + appPath + servletPath, e);
        }
    }
}