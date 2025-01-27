package com.sotska.executor;

import com.sotska.entity.Request;
import com.sotska.entity.Response;
import com.sotska.parser.RequestParser;
import com.sotska.repository.ApplicationRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import static com.sotska.service.ApplicationListener.SEPARATOR;

public class RequestExecutor {
    private static final Logger logger = LoggerFactory.getLogger(RequestExecutor.class);
    private static final String LINE_SEPARATOR = "\r\n";
    private final ApplicationRepository applicationRepository;

    public RequestExecutor(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public void execute(BufferedReader reader, OutputStream outputStream) throws ServletException, IOException, IllegalArgumentException {
        Request request = new RequestParser().parse(reader);
        logger.info("Request: {} start executing.", request);

        Response response = new Response(outputStream);

        String appName = request.getRequestURI().substring(0, request.getRequestURI().indexOf(SEPARATOR));
        String servletPath = request.getRequestURI().substring(appName.length());

        outputStream.write((addLineSeparator("HTTP/1.1 OK")).getBytes());

        HttpServlet httpServlet = applicationRepository.get(appName, servletPath).getPathServletMap().get(servletPath);

        httpServlet.service(request, response);
        response.flushBuffer();
    }

    private static String addLineSeparator(String message) {
        return message + LINE_SEPARATOR + LINE_SEPARATOR;
    }

}
