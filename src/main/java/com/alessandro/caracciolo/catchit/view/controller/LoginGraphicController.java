package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.Main;
import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.controller.LoginController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
    public void handleLoginClick() {
        try {
            UserBean userBean = new UserBean(userTextField.getText(), passwordTextField.getText());

            int typeOfUser = loginController.verifyCredentials(userBean);

            if (typeOfUser == 1) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/RestaurantViewGUI_unique.fxml"));
                Parent rootOrdini = loader.load();

                Stage stageAttuale = (Stage) loginButton.getScene().getWindow();

                stageAttuale.setScene(new Scene(rootOrdini));
                stageAttuale.show();
            } else if (typeOfUser == 2) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/RiderHomePage.fxml"));
                Parent root = loader.load();

                RiderGraphicController riderGraphicController = loader.getController();
                riderGraphicController.initData(userBean.getUsername());

                Stage stageAttuale = (Stage) loginButton.getScene().getWindow();

                stageAttuale.setScene(new Scene(root));
                stageAttuale.show();
            }

        } catch (DAOException e) {
            AlertHandler.showDAOError(e);

        } catch (BusinessException e) {
            AlertHandler.showBusinessError(e);

        } catch (IOException e) {
            AlertHandler.showDAOError(new DAOException("Impossible loading interface: " + e.getMessage()));
        }
    }


    public void handleRegisterClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/RegisterGUI.fxml"));
        Parent root = loader.load();

        RegisterGraphicController controller = loader.getController();
        controller.initialize();

        Stage stageAttuale = (Stage) loginButton.getScene().getWindow();

        stageAttuale.setScene(new Scene(root));
        stageAttuale.show();
    }
}
