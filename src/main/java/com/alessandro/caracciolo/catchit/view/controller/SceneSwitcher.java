package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.Main;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class SceneSwitcher {
    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);

    private SceneSwitcher() { }

    public static void switchToLogin(Node sourceNode) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/LoginGUI.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe("Irreversible UI error: " + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Fatal error: Unable to load the Login interface."));
        }
    }
}
