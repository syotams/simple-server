package com.opal.server.response;

import java.io.IOException;
import java.io.OutputStream;

public class Response {

    private OutputStream out;

    private StringBuilder header = new StringBuilder();


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

    public void flush() throws IOException {
        out.write(header.toString().getBytes());
        out.flush();
    }

    public void notFound() {
        this.header.setLength(0);
        this.addHeader("HTTP/1.1 404 Not Found\n\n");
    }


    public void serverError() {
        this.header.setLength(0);
        this.addHeader("HTTP/1.1 500 Server Error\n\n");
    }
}
