package com.opal.server.strategy.file;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public interface FileStrategy {
    void process(PrintWriter writer, OutputStream out, File file) throws IOException;
}
