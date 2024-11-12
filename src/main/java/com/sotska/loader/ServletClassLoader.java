package com.sotska.loader;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static com.sotska.service.ApplicationListener.SEPARATOR;

public class ServletClassLoader extends ClassLoader {

    public static final String CLASS_EXTENSION = ".class";
    private final String[] paths;

    public ServletClassLoader(String[] paths) {
        this.paths = paths;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String packagePathToClass = name.replace(".", SEPARATOR).concat(CLASS_EXTENSION);
        try {
            for (String path : paths) {
                if (path.endsWith(".jar")) {
                    try (JarFile jarFile = new JarFile(path)) {
                        Enumeration<JarEntry> entries = jarFile.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry jarEntry = entries.nextElement();
                            if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(CLASS_EXTENSION)) {
                                continue;
                            }

                            if (packagePathToClass.equals(jarEntry.getName())) {
                                try (InputStream inputStream = jarFile.getInputStream(jarEntry)) {
                                    return getClass(name, inputStream);
                                }
                            }
                        }
                    }
                } else {
                    try (InputStream inputStream = new FileInputStream(new File(path, packagePathToClass))) {
                        return getClass(name, inputStream);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't load class " + name, e);
        }
        return super.findClass(name);
    }

    private Class<?> getClass(String name, InputStream inputStream) throws IOException {
        byte[] array = IOUtils.toByteArray(inputStream);
        Class<?> clazz = defineClass(name, array, 0, array.length);
        resolveClass(clazz);
        return clazz;
    }
}
