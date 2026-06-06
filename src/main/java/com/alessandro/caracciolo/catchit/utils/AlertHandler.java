package com.alessandro.caracciolo.catchit.utils;

import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import javafx.scene.control.Alert;

public class AlertHandler {

    private AlertHandler() {
    }

    public static void showBusinessError(BusinessException e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning - Business Rule");
        alert.setHeaderText("Unable to complete operation");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static void showDAOError(DAOException e) {
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
}