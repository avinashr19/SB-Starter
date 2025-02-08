package com.main.service;

import com.main.model.DirectoryResponse;
import com.main.model.ProjectRequest;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@AllArgsConstructor
public class StarterProjectAPIService {

    private final DirectoryGeneratorService directoryGeneratorService;
    private final ConfigFileGeneratorService configFileGeneratorService;
    private final JavaClassGeneratorService javaClassGeneratorService;

    public DirectoryResponse generateProject(final ProjectRequest projectRequest)
            throws IOException {

        DirectoryResponse directoryResponse = directoryGeneratorService.generateProjectDirectory(projectRequest);
        configFileGeneratorService.generateConfigFiles(directoryResponse, projectRequest);
        javaClassGeneratorService.generateJavaClasses(directoryResponse, projectRequest);
        compressToZipFile(directoryResponse, projectRequest);

        //deleting project directory
        FileUtils.deleteDirectory(directoryResponse.getProjectDirectory());
        System.out.println("project files and directory deleted from the application memory: -");
        return directoryResponse;
    }

    private void compressToZipFile(DirectoryResponse directoryResponse, ProjectRequest projectRequest)
            throws IOException {
        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        try {
            fos = new FileOutputStream(directoryResponse.getProjectDirectory() + ".zip");
            zipOut = new ZipOutputStream(fos);
            addDirectoryToZip("", directoryResponse.getProjectDirectory(), zipOut);
        } catch (FileNotFoundException e) {
            System.out.println("Error while compressZipFile the project:- " + e.getMessage());
        } finally {
            zipOut.close();
            fos.close();
        }
    }

    private void addDirectoryToZip(String path, File directory, ZipOutputStream zipOut) {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                addDirectoryToZip(path + file.getName() + "/", file, zipOut);
            } else {
                byte[] buffer = new byte[1024];
                try {
                    FileInputStream fis =
                            new FileInputStream(file);
                    zipOut.putNextEntry(new ZipEntry(path + file.getName()));
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zipOut.write(buffer, 0, length);
                    }
                    zipOut.closeEntry();
                    fis.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Error while compressZipFile the project - FileNotFoundException:- " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("Error while compressZipFile the project - IOException:-" + e.getMessage());
                }
            }
        }
    }
}