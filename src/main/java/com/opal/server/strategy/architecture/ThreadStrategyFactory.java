package com.opal.server.strategy.architecture;

import com.opal.server.Config;
import com.opal.server.protocol.TCPProtocol;

import java.util.concurrent.Executors;

public class ThreadStrategyFactory {

    private final static int THREAD_PER_REQUEST = 1;
    private final static int THREAD_POOL = 2;

    public static ThreadStrategyInterface create(int strategy, Config config) {

        switch (strategy) {
            case THREAD_PER_REQUEST:
                return new ThreadPerRequestStrategy();

            case THREAD_POOL:
                return new ThreadPoolStrategy(Executors.newFixedThreadPool(Integer.valueOf(config.get("poolSize"))));

            default:
                return new SingleThreadStrategy(new TCPProtocol());
        }
    }

}
