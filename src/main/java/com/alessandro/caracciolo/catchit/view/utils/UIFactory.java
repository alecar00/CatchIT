package com.alessandro.caracciolo.catchit.view.utils;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIFactory {

    private static final String FONT_SIZE = "-fx-font-size: 14px;";
    private static final String CARD_STYLE =
            "-fx-background-color: #c5e1f5; " +
                    "-fx-background-radius: 10; " +
                    "-fx-padding: 15; " +
                    "-fx-border-color: #2196f3; " +
                    "-fx-border-width: 3; " +
                    "-fx-border-radius: 10;";

    private UIFactory() {
        throw new IllegalStateException("Utility class");
    }

    private static VBox createBaseCard(OrderBean order) {
        VBox card = new VBox(10);
        card.setStyle(CARD_STYLE);

        Label lblId = new Label("ID: #" + order.getIdOrder());
        lblId.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label lblAddress = new Label("Address: " + order.getAddress());
        lblAddress.setStyle(FONT_SIZE);

        Label lblConsumer = new Label("Customer: " + order.getConsumer());
        lblConsumer.setStyle(FONT_SIZE);

        Label lblTime = new Label("Time: " + order.getTime());
        lblTime.setStyle(FONT_SIZE);

        card.getChildren().addAll(lblId, lblAddress, lblConsumer, lblTime);

        return card;
    }

    public static VBox createOrderCard(OrderBean order) {
        VBox card = createBaseCard(order);

        String riderName = (order.getRider() != null) ? order.getRider().getName() : "NOT ASSIGNED";
        Label lblRider = new Label("Rider: " + riderName);
        lblRider.setStyle(FONT_SIZE);

        Label lblStatus = new Label("Status: " + order.getStatus());
        lblStatus.setStyle(FONT_SIZE);

        card.getChildren().addAll(lblRider, lblStatus);

        return card;
    }

    public static VBox createShortOrderCard(OrderBean order) {
        return createBaseCard(order);
    }

    public static Button createAssignButton() {
        Button btnAssign = new Button("Assign");
        btnAssign.setStyle("-fx-background-color: #2196f3; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 20;" +
                "-fx-padding: 8 30; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand;" +
                "-fx-font-size: 13;");
        return btnAssign;
    }

    public static Button createStartDeliveryButton() {
        Button btnDelivery = new Button("Start Delivery");
        btnDelivery.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 8 30; " +
                "-fx-font-weight: bold; -fx-font-size: 13;");

        return btnDelivery;
    }
}