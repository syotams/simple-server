package com.opal.server.strategy.architecture;

import com.opal.server.ConnectionHandlerInterface;

import java.io.IOException;
import java.net.Socket;

public interface ThreadStrategyInterface {

    void process(Socket socket, ConnectionHandlerInterface connectionHandler) throws IOException;

}
