package com.opal.server.strategy.architecture;

import com.opal.server.ConnectionHandlerInterface;

import java.io.IOException;
import java.net.Socket;

public class ThreadPerRequestStrategy implements ThreadStrategyInterface {

    @Override
    public void process(Socket clientSocket, ConnectionHandlerInterface connectionHandler) {
        Thread thread = new Thread(() -> {
            try {
                connectionHandler.onConnection(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }
}
