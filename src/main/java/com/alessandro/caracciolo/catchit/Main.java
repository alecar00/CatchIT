package com.alessandro.caracciolo.catchit;

import com.alessandro.caracciolo.catchit.utils.Printer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Properties properties = loadProperties();

        String interfaceType = properties.getProperty("INTERFACE_TYPE");

        //getting interface info
        if ("gui".equalsIgnoreCase(interfaceType)) {
            //starting gui interface
            startGUI(stage);
        } else if ("cli".equalsIgnoreCase(interfaceType)) {
            //starting cli interface
            startCLI();
        } else {
            Printer.errorPrint("Type of interface specified not supported: " + interfaceType);
            System.exit(1);
        }
    }

    private void startCLI() {

    }

    private void startGUI(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/RestaurantViewGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("CatchIT!");
        stage.setScene(scene);
        stage.show();
    }

    private Properties loadProperties() {
        var properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                Printer.errorPrint("Configuration file not found.");
            }
        } catch (IOException e) {
            Printer.errorPrint(String.format("Error reading configuration file: %s", e.getMessage()));
        }
        return properties;
    }
}
