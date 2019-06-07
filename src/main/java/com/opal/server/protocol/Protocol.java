package com.opal.server.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public interface Protocol {

    void process(BufferedReader reader, OutputStream out) throws IOException;

}
