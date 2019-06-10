package com.opal.server.request;

import com.opal.server.protocol.Protocol;

import java.util.Map;

public class RequestBuilder {

    private String content;

    private Protocol protocol;

    private Map<String, String> headers;


    public RequestBuilder withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public RequestBuilder withProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public Request build() {
        Request request = new Request();
        request.setHeaders(headers);
        request.setProtocol(protocol);
        return request;
    }
}
