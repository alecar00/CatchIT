package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.Main;
import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.controller.ProcessOrderController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
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

    private final String FONT_SIZE = "-fx-font-size: 12;";

    private static final Logger logger = Logger.getLogger(RestaurantGraphicController.class.getName());

    private ProcessOrderController  appController;

    //variable for handling tha highlight
    private VBox lastCardAssigned = null;
    private OrderBean lastOrder = null;

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
        lblAddress.setStyle(FONT_SIZE);

        // Label consumer
        Label lblConsumer = new Label("Cliente: " + order.getConsumer());
        lblConsumer.setStyle(FONT_SIZE);

        //label time
        Label lblTime = new Label("Orario: " + order.getTime());
        lblTime.setStyle(FONT_SIZE);

        // assign button
        Button btnAssegna = new Button("Assegna");
        btnAssegna.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 8 30; " +
                "-fx-font-weight: bold; -fx-font-size: 13;");

        // click action
        btnAssegna.setOnAction(event -> {
            try {
                handleOrderClick(order, card);
            } catch (DAOException e) {
                logger.severe("Error in recovering riders: " + e.getMessage());
            }
        });

        // add to the card
        card.getChildren().addAll(lblId, lblAddress, lblConsumer,lblTime, btnAssegna);

        return card;
    }

    public void updateRidersList(List<RiderBean> riders) {
        // 1. Pulisci la lista attuale per evitare duplicati
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
        lblPermitZTL.setStyle(FONT_SIZE);

        // assign button
        Button btnAssegna = new Button("Assegna");
        btnAssegna.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 8 30; " +
                "-fx-font-weight: bold; -fx-font-size: 13;");

        // click action
        btnAssegna.setOnAction(event -> {
            try {
                handleAssignClick(rider, lastOrder);
            } catch (DAOException e) {
                logger.severe("Error in assigning rider: " + e.getMessage());
                //da aggiungere finestra di errore
            }
        });

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

    private void handleOrderClick(OrderBean orderBean, VBox cardToAssign) throws DAOException{
        List <RiderBean> riderBean = appController.discoverAvailableRiders(orderBean);
        highlightSelectedCard(cardToAssign, lastCardAssigned);
        updateRidersList(riderBean);
        lastCardAssigned = cardToAssign;
        lastOrder = orderBean;
    }

    private void handleAssignClick(RiderBean riderBean, OrderBean orderBean) throws DAOException, BusinessException {
        /**
         * Assegna un ordine specifico a un rider disponibile.
         *
         * @param orderBean L'ordine da assegnare
         * @param riderBean Il rider selezionato
         * @throws DAOException Se il dao non è raggiungibile
         * @throws BusinessException Se abbiamo dati discordanti con il dao
         */
        try{
            appController.assignRider(orderBean, riderBean);

            //da togliere le classi bean per la notifica?? meglio il model??
            showSuccessNotification(orderBean.getIdOrder(), riderBean.getName());

            refreshInterface();

        }catch(DAOException | BusinessException e){
            logger.severe("Error in assigning rider: " + e.getMessage());
        }

    }

    private void refreshInterface(){
        ridersContainer.getChildren().clear();

        lastOrder = null;
        lastCardAssigned = null;

        try{
            List<OrderBean> updatedOrders = appController.discoverPendingOrders();
            updateOrdersList(updatedOrders);
        }catch(BusinessException e){
            logger.severe("Error in getting pending orders: " + e.getMessage());
        }
    }

    private void showSuccessNotification(String orderId, String riderName) {
        notificationText.setText("Ordine #" + orderId + " assegnato a " + riderName);

        notificationBox.setVisible(true);
        notificationBox.setManaged(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            notificationBox.setVisible(false);
            notificationBox.setManaged(false);
        });

        delay.play();
    }

    public void handleLogoutButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/LoginGUI.fxml"));
        Parent root = loader.load();

        Stage stageAttuale = (Stage) logoutButton.getScene().getWindow();

        stageAttuale.setScene(new Scene(root));
        stageAttuale.show();
    }
}
