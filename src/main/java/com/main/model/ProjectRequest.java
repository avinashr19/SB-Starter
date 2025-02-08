package com.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjectRequest {
    private String projectName;
    private String packageName;
    private String artifactId;
    private String projectDescription;
    private List<String> dependencies;

    public ProjectRequest() {
    }


}
