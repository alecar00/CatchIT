package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDAOMemory implements OrderDAO {

    private static List<Order> orders = new ArrayList<>();


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
