package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.Main;
import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.controller.DeliveryController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class RiderGraphicController {

    public HBox notificationBox;
    public Label notificationText;
    @FXML
    private VBox ordersContainer;
    @FXML
    private Button logoutButton;

    private static final String FONT_SIZE = "-fx-font-size: 12;";

    private DeliveryController deliveryController;
    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);

    private String riderId;

    public void initialize() {
        deliveryController = new DeliveryController();
    }
    public void initData(String riderId){
        this.riderId = riderId;
        try{
            List<OrderBean> orderBeans = deliveryController.showOrdersRider(riderId);
            updateOrdersList(orderBeans);
        } catch (DAOException e) {
            logger.severe("Error fetching rider orders during initialization: " + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Unable to load rider orders."));
        }
    }

    private void updateOrdersList(List<OrderBean> orders) {
        ordersContainer.getChildren().clear();

        for (OrderBean order : orders) {
            VBox card = createOrderCard(order);
            ordersContainer.getChildren().add(card);
        }
    }

    private VBox createOrderCard(OrderBean order) {
        VBox card = new VBox(10);

        card.setStyle("-fx-background-color: #c5e1f5; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 15; " +
                "-fx-border-color: #2196f3; " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 10;");

        Label lblId = new Label("ID: #" + order.getIdOrder());
        lblId.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label lblAddress = new Label("Address: " + order.getAddress());
        lblAddress.setStyle(FONT_SIZE);

        Label lblConsumer = new Label("Customer: " + order.getConsumer());
        lblConsumer.setStyle(FONT_SIZE);

        Label lblTime = new Label("Time: " + order.getTime());
        lblTime.setStyle(FONT_SIZE);

        Button btnDelivery = new Button("Start Delivery");
        btnDelivery.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 8 30; " +
                "-fx-font-weight: bold; -fx-font-size: 13;");

        btnDelivery.setOnAction(event -> handleStartDelivery(event, order));

        card.getChildren().addAll(lblId, lblAddress, lblConsumer, lblTime, btnDelivery);

        return card;
    }

    private void handleStartDelivery(ActionEvent event, OrderBean order) {
        try {
            deliveryController.startDelivery(order.getIdOrder(), riderId);
            AlertHandler.showSuccess("Delivery Started", "Order #" + order.getIdOrder() + " is now in delivery.");

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/DeliveryView.fxml"));
            Parent rootOrdini = loader.load();

            DeliveryGraphicController deliveryController = loader.getController();
            deliveryController.initData(order.getIdOrder());

            Stage stageAttuale = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stageAttuale.setScene(new Scene(rootOrdini));
            stageAttuale.show();

        } catch (DAOException e) {
            logger.severe("Error starting delivery: " + e.getMessage());
            AlertHandler.showDAOError(e);
        } catch (BusinessException e) {
            AlertHandler.showBusinessError(e);
        } catch (IOException e){
            logger.severe("Fatal error loading DeliveryView.fxml: " + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Critical UI Error: Unable to load view."));
        }
    }

    public void handleLogoutButton(ActionEvent event) {
        SceneSwitcher.switchToLogin((Node) event.getSource());
    }
}