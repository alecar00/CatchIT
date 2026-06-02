package com.alessandro.caracciolo.catchit.view.controller;

import com.alessandro.caracciolo.catchit.controller.DeliveryController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapType;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DeliveryGraphicController {
    @FXML
    public Button callButton;
    @FXML
    public Button deliveredButton;
    @FXML
    private MapView mapView;

    DeliveryController deliveryController = new DeliveryController();

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
            try{
                handleDeliveredButtonClick("idOrder");
            }catch(DAOException e){

            }
        });
    }

    private void handleDeliveredButtonClick(String idOrder) throws DAOException, BusinessException {
        deliveryController.setOrderCompleted(idOrder);
    }
}
