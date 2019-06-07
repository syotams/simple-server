package com.opal.server.strategy.file;

import java.io.*;

public class TextFileStrategy implements FileStrategy {

    public void process(PrintWriter writer, OutputStream out, File file) throws IOException {
        writer.write("Content-Type: text/html; charset=UTF-8\n\n");

        BufferedReader in = new BufferedReader(new FileReader(file));

        String line;

        while((line = in.readLine()) != null) {
            writer.write(line);
        }

        in.close();
    }

}
