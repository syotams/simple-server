package com.opal.server.strategy.file;

import com.opal.server.response.Response;

import java.io.*;

// TODO: this class is candidate to be redundant
public class TextFileStrategy implements FileStrategy {

    public void process(Response response, File file) throws IOException {
        response.addHeader("Content-Type: text/html; charset=UTF-8\n\n");

        BufferedReader in = new BufferedReader(new FileReader(file));

        String line;

        while((line = in.readLine()) != null) {
            response.addContent(line);
            response.send();
        }

        in.close();
    }

}
