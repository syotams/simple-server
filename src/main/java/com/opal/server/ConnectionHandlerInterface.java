package com.opal.server;

import java.io.IOException;
import java.net.Socket;

public interface ConnectionHandlerInterface {

    void onConnection(Socket clientSocket) throws IOException;

}
