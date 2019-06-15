package com.opal.server.request;

import com.opal.server.protocol.Protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {

    private Map<String, String> headers = new HashMap<>();

    private Protocol protocol;


    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        if(null!=headers) { this.headers = headers; }
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getResource() {
        return headers.get("RESOURCE");
    }

    public String getMethod() {
        return headers.get("METHOD");
    }
}
