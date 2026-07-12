package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.controller.ManageOrdersController;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import com.alessandro.caracciolo.catchit.view.utils.UIFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
        return UIFactory.createOrderCard(order);
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
