package com.opal.server;

import com.opal.server.helpers.FilesHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private Properties properties;

    private static Config instance;


    private Config() {}

    private void init() {
        properties = new Properties();

        try(InputStream props = FilesHelper.getFileFromResources("application.properties")) {
            properties.load(props);
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    public static Config getInstance() {
        if(null == instance) {
            instance = new Config();
            instance.init();
        }

        return instance;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public void set(String key, String value) {
        if(null != value) {
            properties.setProperty(key, value);
        }
    }

    public int getInt(String key) {
        return Integer.valueOf(this.get(key));
    }
}
