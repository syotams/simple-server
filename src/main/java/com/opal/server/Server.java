package com.opal.server;

import com.opal.server.request.RequestHandlerInterface;
import com.opal.server.strategy.architecture.ThreadStrategyInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Server {

    private static int DEFAULT_PORT = 4000;

    private int portNumber = DEFAULT_PORT;

    private ConnectionHandlerInterface connectionHandler;

    private ThreadStrategyInterface threadStrategy;


    private Server(ConnectionHandlerInterface connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public static Server create(ConnectionHandlerInterface connectionHandler, ThreadStrategyInterface threadStrategy) {
        Server server = new Server(connectionHandler);
        server.threadStrategy = threadStrategy;
        return server;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server initiated on port " + portNumber);

            while (true) {
                System.out.println("Waiting for connection");

                Socket clientSocket = serverSocket.accept();

                long startTime = System.nanoTime();

                System.out.println("Incoming connection");

                threadStrategy.process(clientSocket, connectionHandler);

                long elapsedTime = System.nanoTime() - startTime;

                System.out.println("Connection results: elapsed time "
                        + TimeUnit.NANOSECONDS.toMicros(elapsedTime) / 1000f
                        + "ms"
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(int portNumber) {
        if(portNumber > 1024 || portNumber==80) {
            this.portNumber = portNumber;
        }
    }

    public void addHandler(RequestHandlerInterface requestProcessor) {
        this.connectionHandler.addRequestHandler(requestProcessor);
    }
}
