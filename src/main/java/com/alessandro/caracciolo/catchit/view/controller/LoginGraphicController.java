package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.Main;
import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.controller.LoginController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.alessandro.caracciolo.catchit.utils.Printer.errorPrint;

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
    public void handleLoginClick(ActionEvent Event) throws DAOException, BusinessException {
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
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/DeliveryingView.fxml"));
                Parent root = loader.load();

                Stage stageAttuale = (Stage) loginButton.getScene().getWindow();

                stageAttuale.setScene(new Scene(root));
                stageAttuale.show();
            }

        } catch (DAOException e) {
            errorPrint("Errore di connessione al DB: " + e.getMessage());

        } catch (BusinessException e) {
            errorPrint("Credenziali errate: " + e.getMessage());
            // Mostra errore "Password errata"

        } catch (IOException e) {
            errorPrint("Errore irreversibile: Impossibile caricare il file FXML della pagina Ordini.");
            e.printStackTrace();
        }
    }


}
