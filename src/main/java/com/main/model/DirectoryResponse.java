package com.main.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;

@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class DirectoryResponse {

    private File projectDirectory;
    private File checkstyleDirectory;
    private File sourceDirectory;
    private File testDirectory;
    private File resourceDirectory;
    private File testResourceDirectory;
    private File deploymentDirectory;
    private File cupsDirectory;
}
