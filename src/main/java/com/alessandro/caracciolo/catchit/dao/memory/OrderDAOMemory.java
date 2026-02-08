package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.OrderStatus;
import com.alessandro.caracciolo.catchit.query.SearchPendingOrders;
import com.alessandro.caracciolo.catchit.singleton.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOMemory implements OrderDAO {
    @Override
    public void saveOrder(Order order) {

    }

    @Override
    public List<Order> getPendingOrders() {
        //per adesso lo commennto e metto
        List<Order> orders = new ArrayList<>();
        return orders;
    }

    @Override
    public void updateOrder(Order order) {

    }
}
