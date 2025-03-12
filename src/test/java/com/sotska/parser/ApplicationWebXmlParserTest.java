package com.sotska.parser;

import com.sotska.entity.ApplicationSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationWebXmlParserTest {

    private static final String TEST_RESOURCES_PATH = "src/test/resources/";
    public static final String FIRST_KEY = "com.sotska.web.servlet.GoodbyeServlet";
    public static final String SECOND_KEY = "com.sotska.web.servlet.HelloServlet";

    private ApplicationWebXmlParser applicationWebXmlParser;

    @BeforeEach
    void setUp() {
        applicationWebXmlParser = new ApplicationWebXmlParser();
    }

    @Test
    void shouldParseSettingsTest() {
        ApplicationSettings result = applicationWebXmlParser.parse(TEST_RESOURCES_PATH);
        assertNotNull(result);
        assertEquals("resources", result.getName());
        assertEquals(2, result.getServletNameToUrlMap().size());
        assertTrue(result.getServletNameToUrlMap().containsKey(FIRST_KEY));
        assertTrue(result.getServletNameToUrlMap().containsKey(SECOND_KEY));
        assertEquals("/goodbye", result.getServletNameToUrlMap().get(FIRST_KEY));
        assertEquals("/hello", result.getServletNameToUrlMap().get(SECOND_KEY));
    }

    @Test
    void shouldTrowNotFoundExceptionWhileParseSettingsTest() {

        Exception exception = assertThrows(RuntimeException.class, () -> applicationWebXmlParser.parse("non_existing_file"));

        String expectedMessage = "File web.xml not exist by path: non_existing_file/WEB-INF/web.xml";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}