package com.sotska.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UnzipServiceTest {

    private UnzipService unzipService;
    private static final String TEST_RESOURCES_PATH = "src/test/resources/";
    private static final String TEST_WAR_NAME = "test";
    public static final String META_INF = "META-INF";
    public static final String WEB_INF = "WEB-INF";
    private static final String TEST_WAR_FILE = TEST_RESOURCES_PATH + TEST_WAR_NAME + ".war";
    private static final Map<String, List<String>> INNER_DIRS = Map.of(META_INF,
            List.of("MANIFEST.MF", "maven/com.sotska/summer-homework/pom.xml",
                    "maven/com.sotska/summer-homework/pom.properties"), WEB_INF,
            List.of("WEB-INF/web.xml", "classes/com/sotska/web/servlet/GoodbyeServlet.class",
                    "classes/com/sotska/web/servlet/HelloServlet.class"));
    private static final List<String> EXPECTED_WAR_FILES = List.of(META_INF, WEB_INF);

    public static final String SEPARATOR = FileSystems.getDefault().getSeparator();

    @BeforeEach
    void setUp() {
        unzipService = new UnzipService();
    }

    @AfterEach
    void tearDown() throws IOException {

        File unzipFile = new File(TEST_RESOURCES_PATH + TEST_WAR_NAME);
        if (!unzipFile.exists()) {
            return;
        }

        Files.walk(unzipFile.toPath())
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void shouldUnzipWarTest() {
        String outputDir = unzipService.unzip(TEST_WAR_FILE);

        assertEquals("src/test/resources/test", outputDir);
        File unzippedDir = new File(outputDir);
        assertTrue(unzippedDir.exists());
        assertTrue(unzippedDir.isDirectory());

        for (String file : EXPECTED_WAR_FILES) {
            File firstLineDir = new File(TEST_RESOURCES_PATH + TEST_WAR_NAME + SEPARATOR + file);
            assertTrue(firstLineDir.exists());
            assertTrue(firstLineDir.isDirectory());
            checkFilesExist(TEST_RESOURCES_PATH + TEST_WAR_NAME + SEPARATOR + file, INNER_DIRS.get(file));
        }
    }

    @Test
    void shouldThrowExceptionWhileUnzipWarIfFileNotFoundTest() {
        Exception exception = assertThrows(RuntimeException.class, () -> unzipService.unzip("non_existing_file.war"));

        String expectedMessage = "Can't unzip file: non_existing_file.war";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private void checkFilesExist(String parentDir, List<String> pathes) {
        for (String path : pathes) {
            File firstLineDir = new File(parentDir + SEPARATOR + path);
            assertTrue(firstLineDir.exists());
        }
    }
}