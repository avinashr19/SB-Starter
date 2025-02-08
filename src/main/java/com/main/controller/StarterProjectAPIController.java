package com.main.controller;

import com.main.model.DirectoryResponse;
import com.main.model.ProjectRequest;
import com.main.service.StarterProjectAPIService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipFile;

import static com.main.controller.constants.ApplicationConstants.ZIP_EXTENSION;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/project-starter-api/v1")
public class StarterProjectAPIController {
    private final StarterProjectAPIService starterProjectAPIService;

    @PostMapping(value = "/generator")
    public ResponseEntity<Resource> generateProject(final @RequestBody ProjectRequest projectRequest)
            throws IOException {
        log.info("In generateProject controller:-");
        DirectoryResponse
                directoryResponse
                = starterProjectAPIService.generateProject(projectRequest);
        File zipFile = new
                File(projectRequest.getProjectName() + ZIP_EXTENSION);
        byte[] zipBytes = Files.readAllBytes(Paths.get(zipFile.getAbsolutePath()));
        ByteArrayResource resource = new ByteArrayResource(zipBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; "
                + "filename=" + projectRequest.getProjectName() + ZIP_EXTENSION);
        zipFile.delete();
        log.info("zipFile deleted:-");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers).contentLength(zipBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


}
