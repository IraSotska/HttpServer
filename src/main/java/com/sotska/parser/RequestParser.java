package com.sotska.parser;

import com.sotska.entity.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.sotska.service.ApplicationListener.SEPARATOR;

public class RequestParser {
    public static final String SPACE = " ";
    protected static final String PARAM_KEY_VALUE_SEPARATOR = "=";
    private static final String PARAM_START_SYMBOL = "?";
    protected static final String PARAMS_SEPARATOR = "&";

    public Request parse(BufferedReader bufferedReader) {
        Request request = new Request();
        try {
            injectMethodAndUri(bufferedReader, request);
            injectHeaders(bufferedReader, request);
        } catch (IOException e) {
            throw new RuntimeException("Can't parse request", e);
        }

        return request;
    }

    private static void injectMethodAndUri(BufferedReader bufferedReader, Request request) throws IOException {
        String line = bufferedReader.readLine();

        if (line == null) {
            throw new RuntimeException("Not correct request format.");
        }
        request.setMethod(line.substring(0, line.indexOf(SPACE)));
        String URIWithParams = line.substring(line.indexOf(SEPARATOR) + SEPARATOR.length(), line.lastIndexOf(SPACE));

        if (!URIWithParams.contains(PARAM_START_SYMBOL)) {
            request.setRequestURI(URIWithParams);
            return;
        }
        int startParamsIndex = URIWithParams.indexOf(PARAM_START_SYMBOL);
        request.setParameters(parseRequestParams(URIWithParams.substring(startParamsIndex + 1)));
        request.setRequestURI(URIWithParams.substring(0, startParamsIndex));
    }

    protected static Map<String, String> parseRequestParams(String request) {
        Map<String, String> result = new HashMap<>();
        if ("".equals(request)) {
            return result;
        }

        String[] paramKeyValues = request.contains(PARAMS_SEPARATOR) ? request.split(PARAMS_SEPARATOR) : new String[]{request};

        for (String paramKeyValue : paramKeyValues) {
            String[] keyValue = paramKeyValue.split(PARAM_KEY_VALUE_SEPARATOR, 2);
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";
            result.put(URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8), value);
        }
        return result;
    }

    private static void injectHeaders(BufferedReader bufferedReader, Request request) throws IOException {
        String headerLine = bufferedReader.readLine();
        while (headerLine != null && !headerLine.isEmpty()) {
            String[] headerArray = headerLine.split(": ");
            request.addHeader(headerArray[0], headerArray[1]);
            headerLine = bufferedReader.readLine();
        }
    }
}
