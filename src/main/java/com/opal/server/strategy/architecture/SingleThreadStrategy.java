package com.opal.server.strategy.architecture;

import com.opal.server.ConnectionHandlerInterface;

import java.io.IOException;
import java.net.Socket;

public class SingleThreadStrategy implements ThreadStrategyInterface {
    @Override
    public void process(Socket clientSocket, ConnectionHandlerInterface connectionHandler) throws IOException {
        connectionHandler.onConnection(clientSocket);
    }
}
