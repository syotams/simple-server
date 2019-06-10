package com.opal.server.strategy.architecture;

import com.opal.server.ConnectionHandlerInterface;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executor;

public class ThreadPoolStrategy implements ThreadStrategyInterface {

    private Executor executor;


    public ThreadPoolStrategy(Executor executor) {
        this.executor = executor;
    }

    public void process(Socket clientSocket, ConnectionHandlerInterface connectionHandler) {
        executor.execute(() -> {
            try {
                connectionHandler.onConnection(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
