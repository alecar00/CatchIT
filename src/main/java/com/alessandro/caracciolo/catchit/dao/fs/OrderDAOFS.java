package com.alessandro.caracciolo.catchit.dao.fs;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;

import java.util.List;

public class OrderDAOFS implements OrderDAO {

    @Override
    public List<Order> getPendingOrders() {
        return List.of();
    }

    @Override
    public boolean updateOrder(Order order, Rider rider) {

        return false;
    }

    @Override
    public Order getOrderById(String id) throws DAOException {
        return null;
    }
}
