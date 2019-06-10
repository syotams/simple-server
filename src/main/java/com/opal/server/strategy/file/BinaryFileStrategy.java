package com.opal.server.strategy.file;

import com.opal.server.helpers.DateUtils;
import com.opal.server.response.Response;

import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;

public class BinaryFileStrategy implements FileStrategy {

    // TODO: implement using AsynchronousFileChannel
    public void process(Response response, File file) throws IOException {
        long fileSize = file.length();

        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(file.getName());

        response.addHeader(String.format("Content-Length: %d\n", fileSize));
        response.addHeader(String.format("Content-Type: %s\n", mimeType));
        response.addHeader(String.format("Last-Modified: %s\n\n", getLastModified(file)));

        response.sendHeaders();

        InputStream inputStream = new FileInputStream(file);

        //int offset = 0;

        int totalRead;
        int bufferSize = fileSize > 4096 ? 4096 : (int)fileSize;
        byte[] buffer = new byte[bufferSize];
        OutputStream out = response.getOut();

        while((totalRead = inputStream.read(buffer)) > 0) {
            out.write(buffer, 0, totalRead);
            out.flush();
        }

        inputStream.close();
    }

    private String getLastModified(File file) {
        return DateUtils.getDateTimeGMT(file.lastModified());
    }
}

