package com.opal.server;

import com.opal.server.helpers.DateUtils;
import com.opal.server.response.Response;
import com.opal.server.strategy.file.FileStrategy;
import com.opal.server.strategy.file.FileStrategyFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileStreamer {

    // TODO: headers should be refactored into response
    public void streamFile(String fileName, Response res) throws IOException {
        String root = Config.getInstance().get("root");
        File file = new File(System.getProperty("user.dir") + File.separator + root + File.separator + fileName);

        if(!file.isFile()) {
            System.out.println(String.format("File not found %s", fileName));
            throw new FileNotFoundException(fileName);
        }

        String fullPath = file.getName();
        String extension = fullPath.substring(fullPath.lastIndexOf("."));

        res.addHeader("HTTP/1.0 200 OK\n" +
                "Connection: keep-alive\n");

        res.addHeader(String.format("Date: %s\n", DateUtils.getDateGMT()));

        FileStrategy strategy = (new FileStrategyFactory()).create(extension);

        strategy.process(res, file);
    }

}
