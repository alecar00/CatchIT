package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.model.Order;

import java.util.List;

public interface OrderDAO {

    /**
     * Recupera tutti gli ordini.
     * Per il metodo 'discoverPendingOrders' del Controller.
     * @return Lista di tutti gli ordini con lo stato PENDING.
     */
    List<Order> getPendingOrders();

    /**
     * Aggiorna un ordine esistente.
     * Per salvare l'assegnazione del Rider o il cambio di Stato.
     * @param order L'ordine modificato.
     */
    void updateOrder(Order order);
}
