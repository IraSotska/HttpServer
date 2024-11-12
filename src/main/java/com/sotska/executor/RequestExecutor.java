package com.sotska.executor;

import com.sotska.entity.Request;
import com.sotska.entity.Response;
import com.sotska.parser.RequestParser;
import com.sotska.repository.ApplicationServletRepository;

import javax.servlet.ServletException;
import java.io.*;

import static com.sotska.service.ApplicationListener.SEPARATOR;

public class RequestExecutor {
    private final ApplicationServletRepository applicationServletRepository;

    public RequestExecutor(ApplicationServletRepository applicationServletRepository) {
        this.applicationServletRepository = applicationServletRepository;
    }

    public void execute(BufferedReader reader, OutputStream outputStream) throws ServletException, IOException, IllegalArgumentException {
        Request request = new RequestParser().parse(reader);
        Response response = new Response(outputStream);

        String appName = request.getRequestURI().substring(0, request.getRequestURI().indexOf(SEPARATOR));
        String servletPath = request.getRequestURI().substring(appName.length());

        applicationServletRepository.get(appName, servletPath).service(request, response);
    }
}
