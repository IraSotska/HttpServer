package com.sotska;

import com.sotska.creator.ApplicationCreator;
import com.sotska.parser.ApplicationWebXmlParser;
import com.sotska.repository.ApplicationRepository;
import com.sotska.service.ApplicationDeploymentService;
import com.sotska.service.ApplicationListener;
import com.sotska.service.HttpServer;
import com.sotska.service.UnzipService;

public class Starter {

    private static final int PORT = 8080;

    public static final String APPS_PATH = "apps";

    public static void main(String[] args) {
        startHttpServer(APPS_PATH, PORT);
    }

    protected static void startHttpServer(String appsPath, int port) {
        ApplicationRepository applicationRepository = new ApplicationRepository();
        ApplicationDeploymentService applicationDeploymentService = new ApplicationDeploymentService(applicationRepository, new UnzipService(),
                new ApplicationWebXmlParser(), new ApplicationCreator(), appsPath);

        applicationDeploymentService.deployCurrentApplications();

        new Thread(new ApplicationListener(applicationDeploymentService, appsPath)).start();
        new Thread(new HttpServer(applicationRepository, port)).start();
    }
}
