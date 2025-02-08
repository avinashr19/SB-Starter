package com.main.service;

import com.main.model.DirectoryResponse;
import com.main.model.ProjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectoryGeneratorService {

    public DirectoryResponse generateProjectDirectory(final ProjectRequest projectRequest) {
        log.info("Creating project directory:-");
        File projectDir = new File(projectRequest.getProjectName());
        if (!projectDir.exists()) {
            projectDir.mkdirs();
        }
        log.info("Creating check-style directory:");
        File checkstyleDir = new File(projectDir, "config/checkstyle");
        checkstyleDir.mkdirs();

        log.info("Creating deployment directory:-");
        File deploymentDir = new File(projectDir, "deployment/templates");
        deploymentDir.mkdirs();

        log.info("Creating cups directory: -");
        File cupsDir = new File(projectDir, "cups");
        cupsDir.mkdirs();

        log.info("Creating source directory:-");
        File sourceDir = new File(projectDir, "src/main/java/"
                + projectRequest.getPackageName().replace('.', '/'));
        sourceDir.mkdirs();

        File testDir = new File(projectDir, "src/test/java/"
                + projectRequest.getPackageName().replace('.', '/'));
        testDir.mkdirs();

        log.info("Creating resource directory: -");
        File resourceDir = new File(projectDir, "src/main/resources");
        resourceDir.mkdirs();
        File testResourceDir = new File(projectDir, "src/test/resources");
        testResourceDir.mkdirs();

        return DirectoryResponse.builder()
                .projectDirectory(projectDir)
                .checkstyleDirectory(checkstyleDir)
                .sourceDirectory(sourceDir)
                .testDirectory(testDir)
                .resourceDirectory(resourceDir)
                .testResourceDirectory(testResourceDir)
                .cupsDirectory(cupsDir)
                .deploymentDirectory(deploymentDir)
                .build();
    }
}
