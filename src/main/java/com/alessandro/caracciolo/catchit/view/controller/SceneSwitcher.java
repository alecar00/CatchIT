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
    private static final String UI_ERROR_MSG = "Irreversible UI error: ";

    private SceneSwitcher() { }

    public static void switchToLogin(Node sourceNode) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe(() -> UI_ERROR_MSG + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Fatal error: Unable to load the Login interface."));
        }
    }

    public static void switchToManageOrders(Node sourceNode) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/RestaurantView_manageOrders.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe(() -> UI_ERROR_MSG + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Fatal error: Unable to load the Manage Orders interface."));
        }
    }

    public static void switchToPendingOrders(Node sourceNode) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/RestaurantView_ProcessOrder.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe(() -> UI_ERROR_MSG + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Fatal error: Unable to load the Pending Orders interface."));
        }
    }

    public static void switchToRegister(Node sourceNode) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/RegisterView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe(() -> UI_ERROR_MSG + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Fatal error: Unable to load the Register interface."));
        }
    }

    public static void switchToRider(Node sourceNode) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/RiderView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe(() -> UI_ERROR_MSG + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Fatal error: Unable to load the Rider interface."));
        }
    }

    public static void switchToDelivery(Node sourceNode, String idOrder) {
        try {FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/DeliveryView.fxml"));
            Parent root = loader.load();

            DeliveryGraphicController controller = loader.getController();

            controller.initData(idOrder);

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.severe(() -> UI_ERROR_MSG + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Fatal error: Unable to load the Delivery interface."));
        }
    }
}
