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
    public Button CallButton;
    @FXML
    public Button DeliveredButton;
    @FXML
    private MapView mapView;

    DeliveryController deliveryController = new DeliveryController();

    public void initialize() {
        // 1. Imposto una coordinata di esempio (es. zona Tor Vergata)
        Coordinate centerCoord = new Coordinate(41.8558, 12.6224);

        // 2. Creo un marker (il classico pin) di colore rosso e gli assegno la coordinata
        Marker marker = Marker.createProvided(Marker.Provided.RED).setPosition(centerCoord).setVisible(true);

        // 3. LA REGOLA D'ORO: Aspetto che la mappa sia pronta prima di modificarla
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // newValue è 'true' quando la mappa ha finito di caricare

                // Centra la telecamera
                mapView.setCenter(centerCoord);

                // Imposta lo zoom (15 è un buon livello per vedere le strade)
                mapView.setZoom(15);

                // Pianta il pin rosso sulla mappa
                mapView.addMarker(marker);
            }
        });

        // 4. Dico alla mappa di usare OpenStreetMap e avvio il motore di caricamento
        mapView.setMapType(MapType.OSM);
        mapView.initialize();

        DeliveredButton.setOnAction(event -> {
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
