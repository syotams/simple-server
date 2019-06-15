package com.opal.server.request;

import com.opal.server.response.Response;

public interface RequestHandlerInterface {

    boolean handle(Request request, Response response);

}
