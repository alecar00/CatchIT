package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.controller.LoginController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class LoginGraphicController {
    @FXML
    public TextField userTextField;
    @FXML
    public TextField passwordTextField;
    @FXML
    public Button loginButton;
    @FXML
    public Label registerButton;

    LoginController loginController;

    public void initialize(){
        this.loginController = new LoginController();
    }

    @FXML
    public void handleLoginClick(ActionEvent event) {
        try {
            UserBean userBean = new UserBean(userTextField.getText(), passwordTextField.getText());

            int typeOfUser = loginController.verifyCredentials(userBean);

            if (typeOfUser == 1) {
                SceneSwitcher.switchToManageOrders((Node) event.getSource());
            } else if (typeOfUser == 2) {
                SceneSwitcher.switchToRider((Node) event.getSource(), userBean.getUsername());
            }

        } catch (DAOException e) {
            AlertHandler.showDAOError(e);

        } catch (BusinessException e) {
            AlertHandler.showBusinessError(e);

        }
    }


    public void handleRegisterClick(MouseEvent event) {
        SceneSwitcher.switchToRegister((Node) event.getSource());
    }
}
