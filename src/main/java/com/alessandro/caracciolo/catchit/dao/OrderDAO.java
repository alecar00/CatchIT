package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;

import java.util.List;

public interface OrderDAO {

    /**
     * Recupera tutti gli ordini.
     * Per il metodo 'discoverPendingOrders' del Controller.
     * @return Lista di tutti gli ordini con lo stato PENDING.
     */
    List<Order> getPendingOrders()  throws DAOException;

    /**
     * Aggiorna un ordine esistente.
     * Per salvare l'assegnazione del Rider o il cambio di Stato.
     *
     * @param order L'ordine modificato.
     * @return
     */
    boolean updateOrder(Order order, Rider rider) throws DAOException;
    Order getOrderById(String id)  throws DAOException;
}
