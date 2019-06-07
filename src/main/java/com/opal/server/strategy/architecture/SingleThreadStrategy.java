package com.opal.server.strategy.architecture;

import com.opal.server.protocol.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class SingleThreadStrategy implements ThreadStrategyInterface {

    private Protocol protocol;


    public SingleThreadStrategy(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public void process(BufferedReader reader, OutputStream out) throws IOException {
        protocol.process(reader, out);
    }

}
