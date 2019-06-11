package com.opal.server;

import com.opal.server.protocol.HttpProtocol;
import com.opal.server.request.RequestProcessor;
import com.opal.server.strategy.architecture.ThreadStrategyInterface;

public class HttpServer {

    public static Server create(int port, ThreadStrategyInterface threadStrategy) {
        ConnectionHandler connectionHandler = new ConnectionHandler(new HttpProtocol(), new RequestProcessor());

        Server server = Server.create(connectionHandler, threadStrategy);
        server.listen(port);

        return server;
    }

}
