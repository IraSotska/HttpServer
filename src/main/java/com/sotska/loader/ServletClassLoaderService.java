package com.sotska.loader;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;

public class ServletClassLoaderService {

    private static final String CLASSES = "classes";
    private static final String LIB = "lib";
    private static final String WEB_INF = "WEB-INF";

    public ClassLoader getClassLoader(String appPath) {
        File classesDirectory = Paths.get(appPath, WEB_INF, CLASSES).toFile();
        File libDirectory = Paths.get(LIB).toFile();

        File[] jarFiles = getJarsList(libDirectory);
        String[] paths;

        if (jarFiles == null) {
            paths = new String[]{classesDirectory.getPath()};
        } else {
            paths = new String[jarFiles.length + 1];
            for (int i = 0; i < jarFiles.length; i++) {
                paths[i] = jarFiles[i].getPath();
            }

            paths[paths.length - 1] = classesDirectory.getPath();
        }

        return new ServletClassLoader(paths);
    }

    private File[] getJarsList(File libDirectory) {
        FilenameFilter filter = (dir, name) -> name.endsWith(".jar");
        return libDirectory.listFiles(filter);
    }
}
