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

import static com.sotska.service.ApplicationListener.WAR_EXTENSION;


public class ApplicationDeploymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationDeploymentService.class);
    private final ApplicationRepository applicationRepository;
    private final ApplicationWebXmlParser applicationWebXmlParser;
    private final UnzipService unzipService;
    private final ApplicationCreator applicationCreator;
    private final String appsPath;

    public ApplicationDeploymentService(ApplicationRepository applicationRepository, UnzipService unzipService,
                                        ApplicationWebXmlParser applicationWebXmlParser,
                                        ApplicationCreator applicationCreator, String appsPath) {
        this.applicationRepository = applicationRepository;
        this.unzipService = unzipService;
        this.applicationWebXmlParser = applicationWebXmlParser;
        this.applicationCreator = applicationCreator;
        this.appsPath = appsPath;
    }

    public void deployCurrentApplications() {
        Arrays.stream(Objects.requireNonNull(new File(appsPath).listFiles()))
                .filter(file -> file.getName().endsWith(WAR_EXTENSION))
                .forEach(file -> unzipAndDeployAppByPath(file.getAbsolutePath()));

    }

    public void remove(String applicationName) {
        LOGGER.info("Undeploy: {}", applicationName);
        applicationRepository.remove(applicationName);
    }

    public void create(String applicationPath) {
        unzipAndDeployAppByPath(applicationPath);
    }

    public void update(String applicationName) {
        unzipAndDeployAppByPath(applicationName);
    }

    private void unzipAndDeployAppByPath(String applicationPath) {
        LOGGER.info("Deploy: {}", applicationPath);
        String outputPath = unzipService.unzip(applicationPath);
        deploy(outputPath);
    }

    protected void deploy(String appPath) {
        ApplicationSettings settings = applicationWebXmlParser.parse(appPath);
        Application application = applicationCreator.create(appPath, settings);
        applicationRepository.add(application.getAppName(), application);
    }
}
