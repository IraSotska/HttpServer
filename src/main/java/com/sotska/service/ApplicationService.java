package com.sotska.service;

import com.sotska.creator.ApplicationCreator;
import com.sotska.entity.Application;
import com.sotska.entity.ApplicationSettings;
import com.sotska.parser.ApplicationSettingsParser;
import com.sotska.repository.ApplicationServletRepository;


public class ApplicationService {
    private final ApplicationServletRepository applicationServletRepository;
    private final ApplicationSettingsParser applicationSettingsParser;
    private final UnzipService unzipService;
    private final ApplicationCreator applicationCreator;

    public ApplicationService(ApplicationServletRepository applicationServletRepository, UnzipService unzipService,
                              ApplicationSettingsParser applicationSettingsParser, ApplicationCreator applicationCreator) {
        this.applicationServletRepository = applicationServletRepository;
        this.unzipService = unzipService;
        this.applicationSettingsParser = applicationSettingsParser;
        this.applicationCreator = applicationCreator;
    }

    public void remove(String applicationName) {
        applicationServletRepository.remove(applicationName);
    }

    public void create(String applicationPath) {
        String outputPath = unzipService.unzip(applicationPath);
        ApplicationSettings settings = applicationSettingsParser.parse(outputPath);
        Application application = applicationCreator.create(outputPath, settings);
        applicationServletRepository.add(application.getAppName(), application);
    }

    public void update(String applicationName) {

    }
}
