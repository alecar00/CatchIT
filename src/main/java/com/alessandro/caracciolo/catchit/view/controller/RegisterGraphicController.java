package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.controller.RegisterController;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.exceptions.InvalidRegistrationException;
import com.alessandro.caracciolo.catchit.exceptions.UsernameAlreadyUsed;
import com.alessandro.caracciolo.catchit.model.Role;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

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

    RegisterController registerController;

    public String role;

    public void initialize() {
        this.registerController = new RegisterController();
    }

    public void handleRegisterClick(ActionEvent actionEvent) {
        UserBean userBean = new UserBean(userTextField.getText(), passwordTextField.getText(), role);
        try {
            registerController.registerUser(userBean);
            AlertHandler.showSuccess("Registered", "User: " + userBean.getUsername() + " registered!");
        }catch (DAOException e){
            AlertHandler.showDAOError(e);
        }catch (UsernameAlreadyUsed e){
            AlertHandler.showUsernameAlreadyUsedError(e);
        }catch (InvalidRegistrationException e){
            AlertHandler.showInvalidRegistrationError(e);
        }

    }

    public void handleRestaurantClick(ActionEvent actionEvent) {
        role = "RESTAURANT";
    }

    public void handleRiderClick(ActionEvent actionEvent) {
        role = "RIDER";
    }
}
