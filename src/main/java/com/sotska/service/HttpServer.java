package com.sotska.service;

import com.sotska.executor.RequestExecutor;
import com.sotska.repository.ApplicationRepository;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    private final ApplicationRepository applicationRepository;
    private final int port;

    public HttpServer(ApplicationRepository applicationRepository, int port) {
        this.applicationRepository = applicationRepository;
        this.port = port;
    }

    @Override
    public void run() {
        LOGGER.info("Server started by port: {}", port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
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
