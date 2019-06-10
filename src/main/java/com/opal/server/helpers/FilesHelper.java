package com.opal.server.helpers;

import java.io.InputStream;
import java.nio.file.Paths;

public class FilesHelper {

    public static InputStream getFileFromResources(String fileName) {

        ClassLoader classLoader = FilesHelper.class.getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            return inputStream;
        }

    }

    public static void createFolderIfNotExists(String root, String folder) {
        Paths.get(root, folder);
    }
}
