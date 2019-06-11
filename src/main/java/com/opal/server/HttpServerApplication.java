package com.opal.server;


import com.opal.server.helpers.FilesHelper;
import com.opal.server.request.StaticFileProcessor;
import com.opal.server.strategy.architecture.ThreadStrategyFactory;
import com.opal.server.strategy.architecture.ThreadStrategyInterface;

import java.io.File;

public class HttpServerApplication {

    public static void main(String[] args) {
        Config config = Config.getInstance();

        init(args, config);

        ThreadStrategyInterface threadStrategy =
                ThreadStrategyFactory.create(ThreadStrategyFactory.SINGLE_THREAD, Config.getInstance());

        Server server = HttpServer.create(config.getInt("port"), threadStrategy);
        server.addHandler(new StaticFileProcessor());
        server.start();
    }

    private static void init(String[] args, Config config) {
        for(int i=0; i<args.length; i+=2) {
            String value;

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

        FilesHelper.createFolderIfNotExists(System.getProperty("user.dir"), config.get("root"));
    }
}