package com.sotska;

import com.sotska.creator.ApplicationCreator;
import com.sotska.parser.ApplicationWebXmlParser;
import com.sotska.repository.ApplicationRepository;
import com.sotska.service.ApplicationDeploymentService;
import com.sotska.service.ApplicationListener;
import com.sotska.service.HttpServer;
import com.sotska.service.UnzipService;

public class Starter {
    public static void main(String[] args) {
        ApplicationRepository applicationRepository = new ApplicationRepository();
        ApplicationDeploymentService applicationDeploymentService = new ApplicationDeploymentService(applicationRepository, new UnzipService(),
                new ApplicationWebXmlParser(), new ApplicationCreator());

        applicationDeploymentService.deployCurrentApplications();

        new Thread(new ApplicationListener(applicationDeploymentService)).start();
        new HttpServer(applicationRepository).start();
    }
}
