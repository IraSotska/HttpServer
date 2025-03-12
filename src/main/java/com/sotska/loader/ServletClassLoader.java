package com.sotska.loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static com.sotska.service.ApplicationListener.SEPARATOR;

public class ServletClassLoader extends URLClassLoader{

    private static final String CLASSES = "classes";
    private static final String WEB_INF = "WEB-INF";

    public ServletClassLoader(String appPath) throws MalformedURLException {
        super(new URL[]{new URL("file:" + appPath + SEPARATOR + WEB_INF + SEPARATOR + CLASSES + SEPARATOR)});
    }
}
