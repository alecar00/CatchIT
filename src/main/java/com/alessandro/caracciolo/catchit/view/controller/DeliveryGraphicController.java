package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.Main;
import com.alessandro.caracciolo.catchit.controller.DeliveryController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.AlertHandler;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapType;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class DeliveryGraphicController {
    @FXML
    private Button callButton;
    @FXML
    private Button deliveredButton;
    @FXML
    private MapView mapView;

    DeliveryController deliveryController = new DeliveryController();

    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);
    private String idOrder;

    public void initialize() {
        // Imposto una coordinata di esempio (es. Tor Vergata)
        Coordinate centerCoord = new Coordinate(41.8558, 12.6224);

        Marker marker = Marker.createProvided(Marker.Provided.RED).setPosition(centerCoord).setVisible(true);
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                mapView.setCenter(centerCoord);
                mapView.setZoom(15);
                mapView.addMarker(marker);
            }
        });

        mapView.setMapType(MapType.OSM);
        mapView.initialize();

        deliveredButton.setOnAction(event -> {
            try {
                handleDeliveredButtonClick(idOrder);
                AlertHandler.showSuccess("Delivery Completed", "Order successfully marked as delivered.");
            } catch (DAOException exception) {
                AlertHandler.showDAOError(exception);
                logger.severe("Database error during delivery update: " + exception.getMessage());
            } catch (BusinessException e) {
                logger.warning("Business logic violation: " + e.getMessage());
                AlertHandler.showBusinessError(e);
            } catch (Exception e) {
                logger.severe("Unexpected fatal error: " + e.getMessage());
                AlertHandler.showDAOError(e);
            }
        });
    }

    public void initData(String idOrder){
        this.idOrder = idOrder;
    }

    private void handleDeliveredButtonClick(String idOrder) throws DAOException, BusinessException, IOException {
        deliveryController.setOrderCompleted(idOrder);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/RiderView.fxml"));
        Parent root = loader.load();

        RiderGraphicController controller = loader.getController();
        controller.initData(idOrder);

        Stage stageAttuale = (Stage) deliveredButton.getScene().getWindow();
        stageAttuale.setScene(new Scene(root));
        stageAttuale.show();
    }
}