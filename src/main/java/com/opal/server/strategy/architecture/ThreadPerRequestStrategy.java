package com.opal.server.strategy.architecture;

import com.opal.server.protocol.TCPProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class ThreadPerRequestStrategy implements ThreadStrategyInterface {

    @Override
    public void process(BufferedReader in, OutputStream out) {
        Thread thread = new Thread(() -> {
            TCPProtocol httpProtocol = new TCPProtocol();
            try {
                httpProtocol.process(in, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }
}
