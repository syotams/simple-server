package com.opal.server.protocol;

import com.opal.server.request.Request;

import java.io.BufferedReader;
import java.io.IOException;

public interface Protocol {

    Request process(BufferedReader bufferedReader) throws IOException;

}
