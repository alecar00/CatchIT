package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;

import java.util.List;

public interface OrderDAO {

    List<Order> getPendingOrders()  throws DAOException;

    void assignOrder(Order order, Rider rider) throws DAOException;

    Order getOrderById(String orderId)  throws DAOException;

    void setOrderCompleted(String orderId) throws DAOException;

    List<Order> getOrdersByRider(String riderId) throws DAOException;

    void setOrderInDelivery(String orderId) throws DAOException;

    List<Order> getOrders() throws DAOException;
}
