package com.alessandro.caracciolo.catchit.dao.fs;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.model.Order;

import java.util.List;

public class OrderDAOFS implements OrderDAO {
    @Override
    public void saveOrder(Order order) {

    }

    @Override
    public List<Order> getAllOrders() {
        return List.of();
    }

    @Override
    public Order getOrderById(String id) {
        return null;
    }

    @Override
    public void updateOrder(Order order) {

    }
}
