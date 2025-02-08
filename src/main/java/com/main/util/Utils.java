package com.main.util;

import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {

    public static String readFile(final String path) throws IOException {
        var resourceFile = ResourceUtils.getFile("classpath:" + path);

        return Files.readString(Path.of(resourceFile.getAbsolutePath()));

    }
}
