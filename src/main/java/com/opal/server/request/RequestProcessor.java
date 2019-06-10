package com.opal.server.request;

import com.opal.server.response.Response;

import java.util.ArrayList;

public class RequestProcessor implements RequestProcessorInterface {

    private ArrayList<RequestProcessorInterface> processors;

    private RequestProcessor() {}

    public static RequestProcessor make() {
        RequestProcessor processor = new RequestProcessor();
        processor.processors = new ArrayList<>();

        processor.addRequestProcessor(new StaticFileProcessor());

        return processor;
    }

    public void addRequestProcessor(RequestProcessorInterface processor) {
        processors.add(processor);
    }

    @Override
    public boolean process(Request request, Response response) {
        for (RequestProcessorInterface processor : processors) {
            // TODO: should handle exception and eliminate predicate
            if(processor.process(request, response)) return true;
        }

        return false;
    }
}
