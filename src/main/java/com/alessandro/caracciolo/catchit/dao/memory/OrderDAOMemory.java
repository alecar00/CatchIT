package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;

import java.util.ArrayList;
import java.util.List;

public class OrderDAOMemory implements OrderDAO {

    @Override
    public List<Order> getPendingOrders() {
        //per adesso lo commennto e metto
        return new ArrayList<>();
    }

    @Override
    public boolean updateOrder(Order order, Rider rider) {

        return false;
    }

    @Override
    public Order getOrderById(String id) throws DAOException {
        return null;
    }

    @Override
    public boolean setOrderCompleted(String id) throws DAOException{
        return false;
    }
}
