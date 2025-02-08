package com.main.service;

import com.main.model.DirectoryResponse;
import com.main.model.ProjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.main.util.Utils.readFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class JavaClassGeneratorService {

    public String generateJavaClasses(final DirectoryResponse directoryResponse,
                                      final ProjectRequest projectRequest) {
        generateJavaMainClass(directoryResponse, projectRequest);
        //generate main test class
        return "success";
    }

    private static void generateJavaMainClass(DirectoryResponse directoryResponse, ProjectRequest projectRequest) {

        String mainClassTemplateContent = null;
        try {
            mainClassTemplateContent = readFile("template-files/JavaMainClassTemplate");
        } catch (Exception e) {
            log.error("Error in generating the java main class: " + e.getMessage());
        }

        String mainClassName = getMainClassName(projectRequest);
        String mainClassContent = getMainClassContent(projectRequest, mainClassTemplateContent, mainClassName);
        File mainClassFile = new File(directoryResponse.getSourceDirectory(),
                replaceSpecialCharacters(mainClassName) + ".java");
        try (FileWriter writer = new FileWriter(mainClassFile)) {
            writer.write(mainClassContent);
        } catch (IOException e) {
            log.error("Error in generating the java main class: " + e.getMessage());
        }
    }

    private static String replaceSpecialCharacters(String mainClassName) {
        return mainClassName.replaceAll("\\w+", "");
    }

    private static String getMainClassName(ProjectRequest projectRequest) {
        log.info("In getMainClassName: -");
        String projectName = projectRequest.getProjectName();
        StringBuilder projectNameBuilder = new StringBuilder("");
        for (int i = 0; i < projectName.length(); i++) {
            if (i == 0) {
                projectNameBuilder.append((char) (projectName.charAt(i) - 32));
            } else {
                if ((projectName.charAt(i) < 97 || projectName.charAt(i) > 122) && i < projectName.length()) {
                    projectNameBuilder.append((char) (projectName.charAt(i + 1) - 32));
                    i += 1;
                } else {
                    projectNameBuilder.append(projectName.charAt(i));
                }
            }
        }
        log.info("project name is :" + projectNameBuilder);
        return projectNameBuilder.toString() + "Application";
    }

    private static String getMainClassContent(ProjectRequest projectRequest, String
            mainClassContent, String mainClassName) {

        return String.format(mainClassContent, projectRequest.getPackageName(), mainClassName, mainClassName);
    }
}

