package com.opal.server.protocol;

import com.opal.server.request.RequestProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class TCPProtocol implements Protocol {
    private static final int RECEIVING = 0;
    private static final int PROCESSING = 1;
    private static final int CLOSING = 2;

    private int state = RECEIVING;

    private StringBuilder input = new StringBuilder();

    private String lineSep;

    public TCPProtocol() {
        lineSep = System.lineSeparator();
    }

    private void init() {
        input.setLength(0);
        state = RECEIVING;
    }

    public void process(BufferedReader reader, OutputStream out) throws IOException {
        init();

        String inputLine;

        while (!isClosing()) {
            inputLine = reader.readLine();

            if(inputLine!=null) {
                this.processInput(inputLine, out);
            }
        }
    }

    private void processInput(String theInput, OutputStream out) {
        if (state == RECEIVING) {
            if (theInput.equals("")) {
                if(input.length() > 0) {
                    state = PROCESSING;
                }
                else {
                    // Keep alive, enables new connection after, we are not :(
                    state = CLOSING;
                }
            }
            else {
                // this should better be an ArrayList
                input.append(theInput).append(lineSep);
            }
        }

        if (state == PROCESSING) {
            if (theInput.equals("")) {
                RequestProcessor processor = new RequestProcessor(input.toString());
                processor.process(out);

                input.setLength(0);

                // Usually in HTTP/1.0 the connection is closed at this stage, in
                // Http/1.1 Keep alive we keep the connection for further requests to come
                state = CLOSING;
            }
        }
    }

    private boolean isClosing() {
        return state == CLOSING;
    }
}