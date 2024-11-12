package com.sotska.service;

import com.sotska.executor.RequestExecutor;
import com.sotska.repository.ApplicationServletRepository;

import javax.servlet.ServletException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final ApplicationServletRepository applicationServletRepository;

    public HttpServer(ApplicationServletRepository applicationServletRepository) {
        this.applicationServletRepository = applicationServletRepository;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     InputStream inputStream = socket.getInputStream();
                     OutputStream outputStream = socket.getOutputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                ) {
                    new RequestExecutor(applicationServletRepository).execute(reader, outputStream);
                }
            }
        } catch (IOException | ServletException | IllegalArgumentException e) {
            e.printStackTrace();
            start();
        }
    }
}
