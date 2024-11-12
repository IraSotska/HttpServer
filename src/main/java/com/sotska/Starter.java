package com.sotska;

import com.sotska.creator.ApplicationCreator;
import com.sotska.parser.ApplicationSettingsParser;
import com.sotska.repository.ApplicationServletRepository;
import com.sotska.service.ApplicationService;
import com.sotska.service.HttpServer;
import com.sotska.service.UnzipService;
import com.sotska.service.ApplicationListener;

public class Starter {
    public static void main(String[] args) {
        ApplicationServletRepository applicationServletRepository = new ApplicationServletRepository();
        ApplicationService applicationService = new ApplicationService(applicationServletRepository, new UnzipService(),
                new ApplicationSettingsParser(), new ApplicationCreator());

        new ApplicationListener(applicationService).start();
        new HttpServer(applicationServletRepository).start();
    }
}
