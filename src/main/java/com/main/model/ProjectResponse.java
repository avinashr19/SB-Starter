package com.main.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class ProjectResponse {

    private String message;

    private int code;

    private String repositoryURL;
}
