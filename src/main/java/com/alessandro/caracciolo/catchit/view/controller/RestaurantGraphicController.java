package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.Main;
import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.controller.ProcessOrderController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class RestaurantGraphicController {
    @FXML
    public HBox notificationBox;
    @FXML
    public Label notificationText;
    @FXML
    public Button logoutButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label time;
    @FXML
    private VBox ordersContainer;
    @FXML
    private VBox ridersContainer;

    private static final String FONT_SIZE = "-fx-font-size: 12;";
    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);

    private ProcessOrderController appController;
    private VBox lastCardAssigned = null;
    private OrderBean lastOrder = null;

    public void initialize() {
        this.appController = new ProcessOrderController();

        try {
            List<OrderBean> orderBeans = appController.discoverPendingOrders();
            updateOrdersList(orderBeans);
        } catch (Exception e) {
            logger.severe("Error fetching pending orders during initialization: " + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Unable to load pending orders."));
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

        Button btnAssegna = new Button("Assign");
        btnAssegna.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 8 30; " +
                "-fx-font-weight: bold; -fx-font-size: 13;");

        btnAssegna.setOnAction(event -> handleOrderClick(order, card));

        card.getChildren().addAll(lblId, lblAddress, lblConsumer, lblTime, btnAssegna);

        return card;
    }

    public void updateRidersList(List<RiderBean> riders) {
        ridersContainer.getChildren().clear();

        for (RiderBean rider : riders) {
            VBox card = createRiderCard(rider);
            ridersContainer.getChildren().add(card);
        }
    }

    private VBox createRiderCard(RiderBean rider) {
        VBox card = new VBox(10);

        card.setStyle("-fx-background-color: #c5e1f5; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 15; " +
                "-fx-border-color: #2196f3; " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 10;");

        Label lblName = new Label("Name: " + rider.getName());
        lblName.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label lblPermitZTL = new Label("PermitZTL: " + rider.getPermitZTL());
        lblPermitZTL.setStyle(FONT_SIZE);

        Button btnAssegna = new Button("Assign");
        btnAssegna.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 8 30; " +
                "-fx-font-weight: bold; -fx-font-size: 13;");

        btnAssegna.setOnAction(event -> handleAssignClick(rider, lastOrder));

        card.getChildren().addAll(lblName, lblPermitZTL, btnAssegna);

        return card;
    }

    private void highlightSelectedCard(VBox clickedCard, VBox previousCard) {
        if (previousCard != null && previousCard != clickedCard) {
            previousCard.setStyle("-fx-background-color: #c5e1f5; " +
                    "-fx-background-radius: 10; " +
                    "-fx-padding: 15; " +
                    "-fx-border-color: #2196f3; " +
                    "-fx-border-width: 3; " +
                    "-fx-border-radius: 10;");
        }

        clickedCard.setStyle("-fx-background-color: #fec14d; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 15; " +
                "-fx-border-color:#ff6500;" +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 10;");
    }

    private void handleOrderClick(OrderBean orderBean, VBox cardToAssign) {
        try {
            List<RiderBean> riderBean = appController.discoverAvailableRiders(orderBean);
            highlightSelectedCard(cardToAssign, lastCardAssigned);
            updateRidersList(riderBean);
            lastCardAssigned = cardToAssign;
            lastOrder = orderBean;
        } catch (Exception e) {
            logger.severe("Error discovering available riders: " + e.getMessage());
            AlertHandler.showDAOError(new DAOException("An error occurred while fetching available riders."));
        }
    }

    private void handleAssignClick(RiderBean riderBean, OrderBean orderBean) {
        try {
            appController.assignRider(orderBean, riderBean);
            showSuccessNotification(orderBean.getIdOrder(), riderBean.getName());
            refreshInterface();
        } catch (DAOException e) {
            logger.severe("Database error during rider assignment: " + e.getMessage());
            AlertHandler.showDAOError(e);
        } catch (BusinessException e) {
            logger.warning("Business logic violation during rider assignment: " + e.getMessage());
            AlertHandler.showBusinessError(e);
        } catch (Exception e) {
            logger.severe("Unexpected fatal error: " + e.getMessage());
            AlertHandler.showDAOError(new DAOException("A critical system error occurred."));
        }
    }

    private void refreshInterface() {
        ridersContainer.getChildren().clear();
        lastOrder = null;
        lastCardAssigned = null;

        try {
            List<OrderBean> updatedOrders = appController.discoverPendingOrders();
            updateOrdersList(updatedOrders);
        } catch (Exception e) {
            logger.severe("Error refreshing pending orders: " + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Unable to refresh the orders list."));
        }
    }

    private void showSuccessNotification(String orderId, String riderName) {
        notificationText.setText("Order #" + orderId + " successfully assigned to " + riderName);

        notificationBox.setVisible(true);
        notificationBox.setManaged(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            notificationBox.setVisible(false);
            notificationBox.setManaged(false);
        });

        delay.play();
    }

    public void handleLogoutButton() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/LoginGUI.fxml"));
            Parent root = loader.load();

            Stage stageAttuale = (Stage) logoutButton.getScene().getWindow();
            stageAttuale.setScene(new Scene(root));
            stageAttuale.show();
        } catch (IOException e) {
            logger.severe("Irreversible UI error: " + e.getMessage());
            AlertHandler.showDAOError(new DAOException("Fatal error: Unable to load the Login interface."));
        }
    }
}