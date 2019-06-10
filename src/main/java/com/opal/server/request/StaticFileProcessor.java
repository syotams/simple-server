package com.opal.server.request;

import com.opal.server.FileStreamer;
import com.opal.server.response.Response;

import java.io.FileNotFoundException;
import java.io.IOException;

public class StaticFileProcessor implements RequestProcessorInterface {

    public boolean process(Request request, Response response) {
        String resource = request.getResource();

        if(resource.equals("")) {
            resource =  "index.html";
        }

        return download(resource, response);
    }

    private boolean download(String resource, Response response) {
        FileStreamer fileStreamer = new FileStreamer();

        try {
            System.out.println(String.format("Processing request %s", resource));
            fileStreamer.streamFile(resource, response);
            System.out.println(String.format("Processed request %s successfully", resource));
            return true;
        }
        catch (FileNotFoundException e) {
            response.notFound();
        }
        catch (IOException e) {
            response.serverError();
        }

        return false;
    }

}
