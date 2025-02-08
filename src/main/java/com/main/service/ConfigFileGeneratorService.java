package com.main.service;

import com.main.model.DirectoryResponse;
import com.main.model.ProjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.main.controller.constants.ApplicationConstants.*;
import static com.main.util.Utils.readFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConfigFileGeneratorService {
    public void generateConfigFiles(final DirectoryResponse directoryResponse, final ProjectRequest projectRequest) {

        generateApplicationYMLFile(directoryResponse, projectRequest);
    }

    private void generateApplicationYMLFile(DirectoryResponse directoryResponse, ProjectRequest projectRequest) {
        String applicationYmlTemplateContent = null;

        try {
            applicationYmlTemplateContent = readFile("template-files/applicationYmlTemplate");
        } catch (Exception ex) {
            log.error("Error while reading the application yml file: " + ex.getMessage());
        }
        String applicationYmlContent = String.format(applicationYmlTemplateContent, projectRequest.getProjectName());
        File applicationYmlFile = new File(directoryResponse.getResourceDirectory(),
                "application.yml");
        try (FileWriter writer = new FileWriter(applicationYmlFile)) {
            writer.write(applicationYmlContent);
        } catch (IOException e) {
            log.error("Error in generating the application yml file: " + e.getMessage());
        }
    }

    private void generatePomXmlFile(final DirectoryResponse directoryResponse, final ProjectRequest projectRequest) {
        File pomFile = new File(directoryResponse.getProjectDirectory(), "pom.xml");
        String pomTemplateContent = null;
        try {
            pomTemplateContent = readFile("template-files/pomTemplate");
        } catch (IOException e) {
            log.error("Error while reading the pom xml file:- " + e.getMessage());
        }
        try {
            pomFile.createNewFile();
            FileOutputStream pomFileStream = new FileOutputStream(pomFile);
            String pomCode = String.format(pomTemplateContent);

            String pomContentArtifactId = pomCode.replace(PROJECT_ARTIFACT_ID, projectRequest.getArtifactId());
            String pomContentDescription
                    = pomContentArtifactId.replace(PROJECT_DESCRIPTION, projectRequest.getProjectDescription());

            //get and append dependencies
            //hardcode list
            String dependenciesStr = getDependencies(projectRequest.getDependencies());
            String pomDependencies = pomContentDescription.replace(HARD_CODE_DEPENDENCIES, dependenciesStr);

            pomFileStream.write(pomDependencies.getBytes(StandardCharsets.UTF_8));
            pomFileStream.close();
        } catch (IOException e) {
            log.error("Error in generating the pom xml file: " + e.getMessage());
        }
    }

    private String getDependencies(List<String> dependencies) {

        StringBuffer buff = new StringBuffer();
        Map<String, String> dependenciesMap = new HashMap<>();

        return dependencies.toString();
    }
}
