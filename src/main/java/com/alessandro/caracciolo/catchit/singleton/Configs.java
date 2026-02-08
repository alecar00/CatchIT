package com.alessandro.caracciolo.catchit.singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class Configs {
    private static final String FILE_PATH = "src/main/resources/config.properties";
    public static final String LOGGER_NAME = "CatchITLogger";

    private final Properties props = new Properties();
    private static Configs instance = null;

    private Configs() {
        try (FileInputStream propsInput = new FileInputStream(FILE_PATH)) {
            props.load(propsInput);
        } catch (IOException e) {
            Logger.getLogger(LOGGER_NAME).severe(e.getMessage());
        }
    }

    public static synchronized Configs getInstance() {
        if (instance == null) {
            instance = new Configs();
        }
        return instance;
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }
}