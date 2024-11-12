package com.sotska.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.sotska.service.ApplicationListener.WAR_EXTENSION;

public class UnzipService {

    String unzip(String path) {
        String outputDir = path.replace(WAR_EXTENSION, "");

        try {
            unzip(path, outputDir);
        } catch (IOException e) {
            throw new RuntimeException("Can't unzip file: " + path);
        }
        return outputDir;
    }

    public static void unzip(String warFilePath, String outputDir) throws IOException {
        File outputFile = new File(outputDir);
        outputFile.mkdirs();

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(warFilePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                File file = new File(outputDir, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    new File(file.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zipIn.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipIn.closeEntry();
            }
        }
    }
}
