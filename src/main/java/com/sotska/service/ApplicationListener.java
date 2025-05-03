package com.sotska.service;


import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class ApplicationListener implements Runnable {
    public static final String SEPARATOR = FileSystems.getDefault().getSeparator();
    public static final String WAR_EXTENSION = ".war";
    private final ApplicationDeploymentService applicationDeploymentService;
    private final String appsPath;

    public ApplicationListener(ApplicationDeploymentService applicationDeploymentService, String appsPath) {
        this.applicationDeploymentService = applicationDeploymentService;
        this.appsPath = appsPath;
    }

    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path path = Path.of(appsPath);
            path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            boolean poll = true;

            while (poll) {
                poll = pollEvents(watchService);
            }
        } catch (IOException | InterruptedException | ClosedWatchServiceException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean pollEvents(WatchService watchService) throws InterruptedException {
        WatchKey key = watchService.take();
        Path path = (Path) key.watchable();

        for (WatchEvent<?> event : key.pollEvents()) {
            notifyListeners(event.kind(), path.resolve((Path) event.context()).toFile().getName());
        }
        return key.reset();
    }

    protected void notifyListeners(WatchEvent.Kind<?> kind, String applicationName) {
        if (!applicationName.endsWith(WAR_EXTENSION)) {
            return;
        }
        if (kind == ENTRY_CREATE) {
            applicationDeploymentService.create(Paths.get(appsPath, applicationName).toString());
        } else if (kind == ENTRY_MODIFY) {
            applicationDeploymentService.update(applicationName);
        } else if (kind == ENTRY_DELETE) {
            applicationDeploymentService.remove(applicationName);
        }
    }
}
