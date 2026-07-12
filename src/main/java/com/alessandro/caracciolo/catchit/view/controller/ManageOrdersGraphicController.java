package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.controller.ManageOrdersController;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;
import java.util.logging.Logger;

public class ManageOrdersGraphicController {

    @FXML
    public Button logoutButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button showPendingOrdersButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label time;
    @FXML
    private Label rider;
    @FXML
    private VBox ordersContainer;

    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);
    private static final String FONT_SIZE = "-fx-font-size: 12;";
    private ManageOrdersController appController;

    public void initialize() {
        this.appController = new ManageOrdersController();

        try {
            List<OrderBean> orderBeans = appController.discoverOrders();
            updateOrdersList(orderBeans);
        } catch (Exception e) {
            logger.severe("Error fetching orders during initialization: " + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Unable to load orders."));
        }
    }

    public void updateOrdersList(List<OrderBean> orders) {
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

        String rider = (order.getRider() != null) ? order.getRider().getName() : "NOT ASSIGNED";
        Label lblRider = new Label("Rider: " + rider);
        lblRider.setStyle(FONT_SIZE);

        Label lblStatus = new Label("Status: " + order.getStatus());
        lblStatus.setStyle(FONT_SIZE);

        card.getChildren().addAll(lblId, lblAddress, lblConsumer, lblTime, lblRider);

        return card;
    }

    private void refreshInterface() {
        try {
            List<OrderBean> updatedOrders = appController.discoverOrders();
            updateOrdersList(updatedOrders);
        } catch (Exception e) {
            logger.severe("Error refreshing orders: " + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Unable to refresh the orders list."));
        }
    }

    public void handleLogoutButton(ActionEvent event) {
        SceneSwitcher.switchToLogin((Node) event.getSource());
    }

    public void handleUpdateButton() {
        refreshInterface();
    }

    public void handleShowPendingOrdersButton(ActionEvent event) {
        SceneSwitcher.switchToPendingOrders((Node) event.getSource());
    }
}
