package com.alessandro.caracciolo.catchit.dao.fs;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.model.Order;

import java.util.List;

public class OrderDAOFS implements OrderDAO {

    @Override
    public List<Order> getPendingOrders() {
        return List.of();
    }

    @Override
    public void updateOrder(Order order) {

    }
}
