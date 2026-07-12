package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.controller.RegisterController;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.exceptions.InvalidRegistrationException;
import com.alessandro.caracciolo.catchit.exceptions.UsernameAlreadyUsed;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;


public class RegisterGraphicController {

    @FXML
    public TextField userTextField;
    @FXML
    public TextField passwordTextField;
    @FXML
    public Button registerButton;
    @FXML
    public Label cancelButton;
    @FXML
    public ToggleButton restaurantButton;
    @FXML
    public ToggleButton riderButton;
    @FXML
    public HBox riderFieldsBox;
    @FXML
    public TextField nameTextField;

    RegisterController registerController;

    public String role;

    public void initialize() {
        this.registerController = new RegisterController();
    }

    public void handleRegisterClick() {
        if (role == null) {
            AlertHandler.showError("You must select a role!");
            return;
        }

        UserBean userBean = new UserBean(userTextField.getText(), passwordTextField.getText(), role);
        try {
            if ("RIDER".equals(role)) {
                RiderBean riderBean = new RiderBean(userBean.getUsername(), nameTextField.getText());
                registerController.startRiderRegistration(userBean, riderBean);

            } else if ("RESTAURANT".equals(role)) {
                registerController.startRestaurantRegistration(userBean);
            }
            AlertHandler.showSuccess("Registered", "User: " + userBean.getUsername() + " registered!");
        }catch (DAOException e){
            AlertHandler.showDAOError(e);
        }catch (UsernameAlreadyUsed e){
            AlertHandler.showUsernameAlreadyUsedError(e);
        }catch (InvalidRegistrationException e){
            AlertHandler.showInvalidRegistrationError(e);
        }

    }

    public void handleRestaurantClick() {
        role = "RESTAURANT";
        riderFieldsBox.setVisible(false);
        riderFieldsBox.setManaged(false);
    }

    public void handleRiderClick() {
        role = "RIDER";
        riderFieldsBox.setVisible(true);
        riderFieldsBox.setManaged(true);
    }

    public void handleCancelClick(@MonotonicNonNull MouseEvent event) {
        SceneSwitcher.switchToLogin((Node) event.getSource());
    }
}
