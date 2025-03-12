package com.sotska.service;

import com.sotska.executor.RequestExecutor;
import com.sotska.repository.ApplicationRepository;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    public static final int PORT = 8080;

    private final ApplicationRepository applicationRepository;

    public HttpServer(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public void start() {
        LOGGER.info("Server started by port: {}", PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     InputStream inputStream = socket.getInputStream();
                     OutputStream outputStream = socket.getOutputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                ) {
                    new RequestExecutor(applicationRepository).execute(reader, outputStream);
                }
            }
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getCause() + " while processing request: " + e.getMessage());
        }
    }
}
