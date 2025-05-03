package com.sotska.parser;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.sotska.parser.RequestParser.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    @Test
    void shouldParseRequestParamWithOneParam() {
        String paramName = "name";
        String paramValue = "test";
        Map<String, String> params = parseRequestParams(paramName + PARAM_KEY_VALUE_SEPARATOR + paramValue);
        assertNotNull(params);
        assertEquals(1, params.size());
        assertTrue(params.containsKey(paramName));
        assertEquals(paramValue, params.get(paramName));
    }

    @Test
    void shouldParseRequestParamWithTwoParams() {
        String paramName = "name";
        String paramValue = "test";
        String secondParamName = "secondName";
        String secondParamValue = "secondTest";
        Map<String, String> params = parseRequestParams(paramName + PARAM_KEY_VALUE_SEPARATOR + paramValue
                + PARAMS_SEPARATOR + secondParamName + PARAM_KEY_VALUE_SEPARATOR + secondParamValue);
        assertNotNull(params);
        assertEquals(2, params.size());
        assertTrue(params.containsKey(paramName));
        assertEquals(paramValue, params.get(paramName));
        assertTrue(params.containsKey(secondParamName));
        assertEquals(secondParamValue, params.get(secondParamName));
    }

    @Test
    void shouldParseRequestParamWithNoParams() {
        Map<String, String> params = parseRequestParams("");
        assertNotNull(params);
        assertTrue(params.isEmpty());
    }

}