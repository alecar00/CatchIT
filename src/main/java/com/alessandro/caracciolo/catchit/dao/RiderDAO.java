package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.model.Rider;

import java.util.List;

public interface RiderDAO {
    /**
     * Salva un nuovo rider.
     * @param rider Il rider da salvare.
     */
    void saveRider(Rider rider);

    /**
     * Recupera la lista completa dei rider.
     * Utile per popolare la lista 'riders' dentro la classe Restaurant.
     * @return Una lista di oggetti Rider.
     */
    List<Rider> getAllRiders();

    /**
     * NON SONO SICURO
     * Cerca un singolo rider per ID.
     * Corrisponde alla logica di ricerca usata nel Model.
     * @param id L'identificativo del rider (String come da diagramma).
     * @return L'oggetto Rider se trovato, null altrimenti.
     */
    Rider getRiderById(String id);

}
