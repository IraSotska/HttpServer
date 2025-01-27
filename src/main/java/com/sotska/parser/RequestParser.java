package com.sotska.parser;

import com.sotska.entity.Request;

import java.io.BufferedReader;
import java.io.IOException;

import static com.sotska.service.ApplicationListener.SEPARATOR;

public class RequestParser {
    public static final String SPACE = " ";

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
        request.setRequestURI(line.substring(line.indexOf(SEPARATOR) + SEPARATOR.length(), line.lastIndexOf(SPACE)));
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
