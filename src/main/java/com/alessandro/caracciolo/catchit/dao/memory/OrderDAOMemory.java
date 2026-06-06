package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.OrderStatus;
import com.alessandro.caracciolo.catchit.model.Rider;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOMemory implements OrderDAO {
    static List<Order> orders =  new ArrayList<>();

    static{
        orders.add(new Order("1",
                "Via Roma 10",
                "Mario Rossi",
                "3331234567",
                Time.valueOf("20:30:00"),
                OrderStatus.PENDING));
    }


    @Override
    public List<Order> getPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>();
            for (Order order : orders) {
                if (order.getStatus().equals(OrderStatus.PENDING)){
                    pendingOrders.add(order);
                }
            }
        return pendingOrders;
    }

    @Override
    public void updateOrder(Order order, Rider rider) {
        order.setRider(rider);
        order.setStatus(OrderStatus.ASSIGNED);
    }

    @Override
    public Order getOrderById(String id) {
        for (Order order : orders) {
            if (order.getIdOrder().equals(id)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public void setOrderCompleted(String id){
        for (Order order : orders) {
            if (order.getIdOrder().equals(id)) {
                order.setStatus(OrderStatus.COMPLETED);
            }
        }
    }
}
