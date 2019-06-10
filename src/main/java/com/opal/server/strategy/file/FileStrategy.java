package com.opal.server.strategy.file;

import com.opal.server.response.Response;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public interface FileStrategy {
    void process(Response response, File file) throws IOException;
}
