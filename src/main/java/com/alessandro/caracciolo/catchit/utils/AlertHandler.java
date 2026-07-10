package com.alessandro.caracciolo.catchit.utils;

import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.exceptions.InvalidRegistrationException;
import com.alessandro.caracciolo.catchit.exceptions.UsernameAlreadyUsed;
import javafx.scene.control.Alert;

public class AlertHandler {

    private AlertHandler() {
    }

    private static final String BUSINNES = "Warning - Business Rule";

    public static void showBusinessError(BusinessException e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(BUSINNES);
        alert.setHeaderText("Unable to complete operation");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static void showDAOError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("System Error");
        alert.setHeaderText("Data persistence issue");
        alert.setContentText("A technical issue occurred. Details: " + e.getMessage());
        alert.showAndWait();
    }

    public static void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showUsernameAlreadyUsedError(UsernameAlreadyUsed e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(BUSINNES);
        alert.setHeaderText("Username not valid!");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static void showInvalidRegistrationError(InvalidRegistrationException e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(BUSINNES);
        alert.setHeaderText("Unable to complete operation");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showNotificationError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Notification Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}