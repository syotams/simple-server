package com.opal.server.request;

import com.opal.server.FileStreamer;
import com.opal.server.response.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class RequestProcessor {

    private final String inputString;


    public RequestProcessor(String input) {
        this.inputString = input;
    }

    public void process(OutputStream out) {
        Request request = Request.build(inputString);
        Response response = Response.build(out);

        String resource = request.getResource();

        if(resource.equals("")) {
            resource =  "index.html";
        }

        download(resource, response);
    }

    private void download(String resource, Response response) {
        FileStreamer fileStreamer = new FileStreamer();

        try {
            System.out.println(String.format("Processing request %s", resource));
            fileStreamer.streamFile(resource, response);
            System.out.println(String.format("Processed request %s successfully", resource));
        }
        catch (FileNotFoundException e) {
            response.notFound();
        }
        catch (IOException e) {
            response.serverError();
        }
    }
}
