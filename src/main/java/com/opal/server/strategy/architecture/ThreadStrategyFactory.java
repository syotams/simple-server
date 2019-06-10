package com.opal.server.strategy.architecture;

import com.opal.server.Config;

import java.util.concurrent.Executors;

public class ThreadStrategyFactory {

    public final static int SINGLE_THREAD = 0;
    public final static int THREAD_PER_REQUEST = 1;
    public final static int THREAD_POOL = 2;

    public static ThreadStrategyInterface create(int strategy, Config config) {

        switch (strategy) {
            case THREAD_PER_REQUEST:
                return new ThreadPerRequestStrategy();

            case THREAD_POOL:
                return new ThreadPoolStrategy(
                    Executors.newFixedThreadPool(config.getInt("poolSize"))
                );

            default:
                return new SingleThreadStrategy();
        }
    }

}
