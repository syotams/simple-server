package com.opal.server;

import com.opal.server.request.RequestHandlerInterface;

import java.io.IOException;
import java.net.Socket;

public interface ConnectionHandlerInterface {

    void onConnection(Socket clientSocket) throws IOException;

    void addRequestHandler(RequestHandlerInterface requestHandler);
}
