package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.model.Restaurant;

public interface RestaurantDAO {
    /**
     * Salva l'istanza del ristorante.
     * @param restaurant L'oggetto ristorante.
     */
    void saveRestaurant(Restaurant restaurant);

    /**
     * Recupera i dati del ristorante (es. Nome).
     * @return L'istanza del Ristorante
     */
    Restaurant getRestaurant();

}
