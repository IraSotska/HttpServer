package com.sotska.service;

import com.sotska.creator.ApplicationCreator;
import com.sotska.entity.Application;
import com.sotska.entity.ApplicationSettings;
import com.sotska.parser.ApplicationWebXmlParser;
import com.sotska.repository.ApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

import static com.sotska.service.ApplicationListener.APPS_PATH;
import static com.sotska.service.ApplicationListener.WAR_EXTENSION;


public class ApplicationDeploymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationDeploymentService.class);
    private final ApplicationRepository applicationRepository;
    private final ApplicationWebXmlParser applicationWebXmlParser;
    private final UnzipService unzipService;
    private final ApplicationCreator applicationCreator;

    public ApplicationDeploymentService(ApplicationRepository applicationRepository, UnzipService unzipService,
                                        ApplicationWebXmlParser applicationWebXmlParser, ApplicationCreator applicationCreator) {
        this.applicationRepository = applicationRepository;
        this.unzipService = unzipService;
        this.applicationWebXmlParser = applicationWebXmlParser;
        this.applicationCreator = applicationCreator;
    }

    public void deployCurrentApplications() {
        Arrays.stream(Objects.requireNonNull(new File(APPS_PATH).listFiles()))
                .filter(file -> file.getName().endsWith(WAR_EXTENSION))
                .forEach(file -> deployByPath(file.getAbsolutePath()));

    }

    public void remove(String applicationName) {
        LOGGER.info("Undeploy: {}", applicationName);
        applicationRepository.remove(applicationName);
    }

    public void create(String applicationPath) {
        deployByPath(applicationPath);
    }

    public void update(String applicationName) {
        deployByPath(applicationName);
    }

    private void deployByPath(String applicationPath) {
        LOGGER.info("Deploy: {}", applicationPath);
        String outputPath = unzipService.unzip(applicationPath);
        ApplicationSettings settings = applicationWebXmlParser.parse(outputPath);
        Application application = applicationCreator.create(outputPath, settings);
        applicationRepository.add(application.getAppName(), application);
    }
}
