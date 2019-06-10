package com.opal.server.protocol;

import com.opal.server.request.Request;
import com.opal.server.request.RequestBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpProtocol implements Protocol {
    private final int IDLE = 0;
    private final int PROCESSING_HEADERS = 1;
    private final int PROCESSING_DATA = 2;
    private final int CLOSING = 9;

    private final static String LINE_SEPARATOR = System.lineSeparator();

    private int state = IDLE;

    private String method, protocolVersion, resource;


    public Request process(BufferedReader bufferedReader) throws IOException {
        String clientInput;
        ArrayList<String> headers = new ArrayList<>();
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.withProtocol(this);

        state = IDLE;

        while (CLOSING!=state && null != (clientInput = bufferedReader.readLine())) {
            switch (state) {
                case IDLE:
                    parseProtocol(clientInput);
                    state = PROCESSING_HEADERS;
                    break;

                case PROCESSING_HEADERS:
                    if(clientInput.equals("")) {
                        requestBuilder.withHeaders(parseHeaders(headers));

                        if(method.equals("GET")) {
                            state = CLOSING;
                        }
                        else {
                            state = PROCESSING_DATA;
                        }
                        break;
                    }

                    headers.add(clientInput);
                    break;

                case PROCESSING_DATA:
                    // TODO: support for PUT, PATCH, POST etc..
                    requestBuilder.withContent(clientInput); // process the first line and stop
                    state = CLOSING;
                    break;
            }

            System.out.println(clientInput);
        }

        state = IDLE;

        return requestBuilder.build();
    }

    private void parseProtocol(String input) {
        Matcher matcher = Pattern.compile("^(.*?) \\/(.*?) HTTP\\/(.*)").matcher(input);

        if(matcher.find()) {
            method = matcher.group(1);
            resource = matcher.group(2).trim();
            protocolVersion = matcher.group(3);
        }
    }

    private Map<String, String> parseHeaders(ArrayList<String> inputArr) {
        Map<String, String> headers = new HashMap();

        for(String line : inputArr) {
            String[] keyMap = line.split(":");

            if(keyMap.length==2) {
                headers.put(keyMap[0].trim(), keyMap[1].trim());
            }
        }

        return headers;
    }

    public String getMethod() {
        return method;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public String getResource() {
        return resource;
    }

    // TODO: reset is redundant ?!?
    @Override
    public void reset() {
        state = IDLE;
    }
}