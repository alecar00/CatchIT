package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.controller.ProcessOrderController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class RestaurantGraphicController {
    @FXML
    private Label statusLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label Time;
    @FXML
    private VBox ordersContainer;
    @FXML
    private VBox ridersContainer;

    private ProcessOrderController  appController;

    //variable for handling tha highlight
    private VBox lastCardAssigned = null;

    public void initialize() {
        this.appController = new ProcessOrderController();

        List<OrderBean> orderBeans = appController.discoverPendingOrders();
        updateOrdersList(orderBeans);
    }

    // Metodo per popolare la lista graficamente
    public void updateOrdersList(List<OrderBean> orders) {
        // 1. Pulisci la lista attuale per evitare duplicati
        ordersContainer.getChildren().clear();

        // 2. Cicla su ogni ordine e crea la "Card" grafica
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

        // Label ID
        Label lblId = new Label("ID: #" + order.getIdOrder());
        lblId.setFont(Font.font("System", FontWeight.BOLD, 14));

        // Label address
        Label lblAddress = new Label("Indirizzo: " + order.getAddress());
        lblAddress.setStyle("-fx-font-size: 12;");

        // Label consumer
        Label lblConsumer = new Label("Cliente: " + order.getConsumer());
        lblConsumer.setStyle("-fx-font-size: 12;");

        //label time
        Label lblTime = new Label("Orario: " + order.getTime());
        lblTime.setStyle("-fx-font-size: 12;");

        // assign button
        Button btnAssegna = new Button("Assegna");
        btnAssegna.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 8 30; " +
                "-fx-font-weight: bold; -fx-font-size: 13;");

        // click action
        btnAssegna.setOnAction(event -> handleOrderClick(order, card));

        // add to the card
        card.getChildren().addAll(lblId, lblAddress, lblConsumer,lblTime, btnAssegna);

        return card;
    }

    public void updateRidersList(List<RiderBean> riders) {
        // 1. Pulisci la lista attuale per evitare duplicati
        //ordersContainer.getChildren().clear();
        ridersContainer.getChildren().clear();

        // 2. Cicla su ogni ordine e crea la "Card" grafica
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

        // Label ID
        Label lblName = new Label("Name: " + rider.getName());
        lblName.setFont(Font.font("System", FontWeight.BOLD, 14));

        // Label address
        Label lblPermitZTL = new Label("PermitZTL: " + rider.getPermitZTL());
        lblPermitZTL.setStyle("-fx-font-size: 12;");

        // assign button
        Button btnAssegna = new Button("Assegna");
        btnAssegna.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 8 30; " +
                "-fx-font-weight: bold; -fx-font-size: 13;");

        // click action
        btnAssegna.setOnAction(event -> handleAssignClick(rider));

        // add to the card
        card.getChildren().addAll(lblName, lblPermitZTL, btnAssegna);

        return card;
    }

    private void highlightSelectedCard(VBox clickedCard, VBox previousCard) {

        // 1. Reset the previously selected button (back to BLUE)
        if (previousCard != null && previousCard != clickedCard) {
            previousCard.setStyle("-fx-background-color: #c5e1f5; " +
                    "-fx-background-radius: 10; " +
                    "-fx-padding: 15; " +
                    "-fx-border-color: #2196f3; " +
                    "-fx-border-width: 3; " +
                    "-fx-border-radius: 10;");
        }

        // 2. Highlight the newly clicked button (turn Orange)
        clickedCard.setStyle("-fx-background-color: #fec14d; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 15; " +
                "-fx-border-color:#ff6500;" +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 10;");
    }

    private void handleOrderClick(OrderBean orderBean, VBox cardToAssign) {
        List <RiderBean> riderBean = appController.discoverAvailableRiders(orderBean);
        highlightSelectedCard(cardToAssign, lastCardAssigned);
        updateRidersList(riderBean);
        lastCardAssigned = cardToAssign;
    }

    private void handleAssignClick(RiderBean rider) {

    }
}
