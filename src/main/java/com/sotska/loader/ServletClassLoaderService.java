package com.sotska.loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static com.sotska.service.ApplicationListener.SEPARATOR;

public class ServletClassLoaderService {

    private static final String CLASSES = "classes";
    private static final String WEB_INF = "WEB-INF";

    public URLClassLoader getClassLoader(String appPath) throws MalformedURLException {

        var urls = new URL[]{new URL("file:" + appPath + SEPARATOR + WEB_INF + SEPARATOR + CLASSES + SEPARATOR)};
        ClassLoader classLoader = ServletClassLoaderService.class.getClassLoader();
        return new URLClassLoader(urls, classLoader);
    }
}
