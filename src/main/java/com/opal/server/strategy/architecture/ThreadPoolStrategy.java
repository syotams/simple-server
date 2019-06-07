package com.opal.server.strategy.architecture;

import com.opal.server.protocol.TCPProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executor;

public class ThreadPoolStrategy implements ThreadStrategyInterface {

    private Executor executor;


    public ThreadPoolStrategy(Executor executor) {
        this.executor = executor;
    }

    public void process(BufferedReader in, OutputStream out) {
        executor.execute(() -> {
            TCPProtocol httpProtocol = new TCPProtocol();
            try {
                httpProtocol.process(in, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
