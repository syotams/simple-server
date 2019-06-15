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


    public Request process(BufferedReader bufferedReader) throws IOException {
        String clientInput;
        ArrayList<String> headers = new ArrayList<>();
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.withProtocol(this);
        Map<String, String> headersMap = null;

        int state = IDLE;

        while (CLOSING!=state && null != (clientInput = bufferedReader.readLine())) {
            switch (state) {
                case IDLE:
                    headersMap = parseProtocol(clientInput);
                    state = PROCESSING_HEADERS;
                    break;

                case PROCESSING_HEADERS:
                    if(clientInput.equals("")) {
                        headersMap.putAll(parseHeaders(headers));
                        requestBuilder.withHeaders(headersMap);

                        if(headersMap.get("METHOD").equals("GET")) {
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
                    requestBuilder.withContent(clientInput); // handle the first line and stop
                    state = CLOSING;
                    break;
            }
        }

        return requestBuilder.build();
    }

    private Map<String, String> parseProtocol(String input) {
        Matcher matcher = Pattern.compile("^(.*?) \\/(.*?) (HTTPS?)\\/(.*)").matcher(input);
        Map<String, String> headers = new HashMap();

        if(matcher.find()) {
            headers.put("METHOD", matcher.group(1));
            headers.put("RESOURCE", matcher.group(2));
            headers.put("PROTOCOL", matcher.group(3));
            headers.put("VERSION", matcher.group(4));
        }

        return headers;
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

}