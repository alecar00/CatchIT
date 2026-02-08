package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.model.Order;

import java.util.List;

public class OrderDAODB implements OrderDAO {
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
