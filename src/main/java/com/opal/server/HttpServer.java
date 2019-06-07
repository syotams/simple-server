package com.opal.server;


import com.opal.server.helpers.FilesHelper;
import com.opal.server.strategy.architecture.ThreadStrategyFactory;
import com.opal.server.strategy.architecture.ThreadStrategyInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private ThreadStrategyInterface strategy;

    private Config config;


    public static void main(String[] args) {
        HttpServer server = new HttpServer();

        try {
            server.run(args);
        }
        catch (Exception e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    private void run(String[] args) {
        init(args);

        int portNumber = Integer.parseInt(config.get("port"));

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.err.println("Server opened visit at: http://localhost:" + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setKeepAlive(true);
                clientSocket.setSoTimeout(3000);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                strategy.process(in, clientSocket.getOutputStream());

                clientSocket.getOutputStream().flush();
                in.close();
            }
        }
        catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private void init(String[] args) {
        config = Config.getInstance();

        for(int i=0; i<args.length; i+=2) {
            String value = null;

            if(args.length > i+1) {
                value = args[i+1];
            }
            else {
                throw new IllegalArgumentException(args[i] + " is defined but does not have a value");
            }

            if(args[i].equalsIgnoreCase("-p") || args[i].equalsIgnoreCase("--port")) {
                config.set("port", value);
            }
            else if(args[i].equalsIgnoreCase("-s") || args[i].equalsIgnoreCase("--strategy")) {
                config.set("strategy", value);
            }
            else if(args[i].equalsIgnoreCase("-pl") || args[i].equalsIgnoreCase("--pool")) {
                config.set("poolSize", value);
            }
            else if(args[i].equalsIgnoreCase("--root")) {
                if(new File(value).isDirectory()) {
                    config.set("root", value);
                }
            }
        }

        strategy = ThreadStrategyFactory.create(Integer.valueOf(config.get("strategy")), config);

        FilesHelper.createFolderIfNotExists(System.getProperty("user.dir"), config.get("root"));
    }
}