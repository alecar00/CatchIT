package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.controller.DeliveryController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import com.alessandro.caracciolo.catchit.view.utils.UIFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
        VBox card = UIFactory.createShortOrderCard(order);

        Button btnStartDelivery = UIFactory.createStartDeliveryButton();
        btnStartDelivery.setOnAction(event -> {handleStartDelivery(event, order);});

        card.getChildren().add(btnStartDelivery);
        return card;

    }

    private void handleStartDelivery(ActionEvent event, OrderBean order) {
        try {
            deliveryController.startDelivery(order.getIdOrder(), riderId);
            AlertHandler.showSuccess("Delivery Started", "Order #" + order.getIdOrder() + " is now in delivery.");

            SceneSwitcher.switchToDelivery((Node) event.getSource(), order.getIdOrder());

        } catch (DAOException e) {
            logger.severe("Error starting delivery: " + e.getMessage());
            AlertHandler.showDAOError(e);
        } catch (BusinessException e) {
            AlertHandler.showBusinessError(e);
        }
    }

    public void handleLogoutButton(ActionEvent event) {
        SceneSwitcher.switchToLogin((Node) event.getSource());
    }
}