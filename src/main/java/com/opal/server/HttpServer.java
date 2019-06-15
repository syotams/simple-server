package com.opal.server;

import com.opal.server.protocol.HttpProtocol;
import com.opal.server.strategy.architecture.ThreadStrategyFactory;
import com.opal.server.strategy.architecture.ThreadStrategyInterface;

public class HttpServer {

    public static Server create(int port, int threadStrategyType) {

        ThreadStrategyInterface threadStrategy =
                ThreadStrategyFactory.create(threadStrategyType, Config.getInstance());

        ConnectionHandler connectionHandler = new ConnectionHandler(new HttpProtocol());

        Server server = Server.create(connectionHandler, threadStrategy);
        server.listen(port);

        return server;
    }

}
