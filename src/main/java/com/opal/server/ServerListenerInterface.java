package com.opal.server;

import com.opal.server.request.Request;
import com.opal.server.response.Response;

public interface ServerListenerInterface {

    void onConnection(Request request, Response response);

}
