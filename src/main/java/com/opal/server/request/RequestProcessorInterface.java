package com.opal.server.request;

import com.opal.server.response.Response;

public interface RequestProcessorInterface {

    boolean process(Request request, Response response);

}
