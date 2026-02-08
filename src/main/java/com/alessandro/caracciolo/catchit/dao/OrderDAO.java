package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.model.Order;

import java.util.List;

public interface OrderDAO {
    /**
     * Salva un nuovo ordine.
     * @param order L'ordine appena creato (stato PENDING).
     */
    void saveOrder(Order order);

    /**
     * Recupera tutti gli ordini.
     * Per il metodo 'discoverPendingOrders' del Controller.
     * @return Lista di tutti gli ordini.
     */
    List<Order> getAllOrders();

    /**
     * Recupera un ordine specifico.
     * @param id L'ID dell'ordine (String).
     * @return L'oggetto Order.
     */
    Order getOrderById(String id);

    /**
     * Aggiorna un ordine esistente.
     * Per salvare l'assegnazione del Rider o il cambio di Stato.
     * @param order L'ordine modificato.
     */
    void updateOrder(Order order);
}
