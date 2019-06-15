package com.opal.server.response;

import java.io.IOException;
import java.io.OutputStream;

public class Response {

    private OutputStream out;

    private StringBuilder header = new StringBuilder();

    private StringBuilder content = new StringBuilder();

    private boolean isHeaderSent;

    public Response() throws Exception {
        throw new Exception("Cannot create object use build method");
    }

    private Response(OutputStream out) {
        this.out = out;
    }

    public static Response build(OutputStream out) {
        return new Response(out);
    }

    public OutputStream getOut() {
        return out;
    }

    public void addHeader(String text) {
        header.append(text);
    }

    public void sendHeaders() throws IOException {
        if(isHeaderSent) {
            throw new RuntimeException("Headers already sent");
        }

        out.write(header.toString().getBytes());
        out.flush();
        header.setLength(0);
        isHeaderSent = true;
    }

    public void send() throws IOException {
        if(!isHeaderSent) {
            sendHeaders();
        }

        out.write(content.toString().getBytes());
        out.close();
    }

    public void notFound() {
        this.header.setLength(0);
        this.addHeader("HTTP/1.1 404 Not Found\n\n");
    }


    public void serverError() {
        this.header.setLength(0);
        this.addHeader("HTTP/1.1 500 Server Error\n\n");
    }

    public void addContent(String data) {
        content.append(data);
    }
}
