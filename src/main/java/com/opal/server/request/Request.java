package com.opal.server.request;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {

    private final String rawHeader;

    private Map<String, String> headers = new HashMap<>();

    private String method;

    private String resource;

    private String protocolVersion;


    public Request() throws Exception {
        throw new Exception("Cannot create object use build method");
    }

    private Request(String header) {
        this.rawHeader = header;
    }

    public static Request build(String header) {
        Request request = new Request(header);

        request.parseResource();
        request.parseHeaders();

        return request;
    }

    private void parseResource() {
        Matcher matcher = Pattern.compile("^(.*?) \\/(.*?) HTTP\\/(.*)").matcher(rawHeader);

        if(matcher.find()) {
            method = matcher.group(1);
            resource = matcher.group(2).trim();
            protocolVersion = matcher.group(3);
        }
    }

    private void parseHeaders() {
        String[] headersLine = rawHeader.split(System.lineSeparator());

        for (String line : headersLine) {
            parseLine(line);
        }
    }

    private void parseLine(String line) {
        String[] keyMap = line.split(":");

        if(keyMap.length==2) {
            headers.put(keyMap[0].trim(), keyMap[1].trim());
        }
    }

    public String getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }
}
