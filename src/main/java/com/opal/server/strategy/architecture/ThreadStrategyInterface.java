package com.opal.server.strategy.architecture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public interface ThreadStrategyInterface {

    void process(BufferedReader reader, OutputStream out) throws IOException;

}
