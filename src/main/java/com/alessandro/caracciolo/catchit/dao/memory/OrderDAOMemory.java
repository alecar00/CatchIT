package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
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
        orders.add(new Order("2",
                "Piazza Dante 5",
                "Marco",
                "3339876543",
                new Rider("R1", "Mario Rossi", true),
                Time.valueOf("19:30:00"),
                OrderStatus.ASSIGNED ));
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
    public Order getOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getIdOrder().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public void setOrderCompleted(String orderId){
        for (Order order : orders) {
            if (order.getIdOrder().equals(orderId)) {
                order.setStatus(OrderStatus.COMPLETED);
            }
        }
    }

    @Override
    public List<Order> getOrdersByRider(String riderId) throws DAOException {
        List <Order> riderOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getRider() != null && order.getRider().getIdRider().equals(riderId)) {
                riderOrders.add(order);
            }
        }
        return riderOrders;
    }

    @Override
    public void setOrderInDelivery(String orderId) throws DAOException {
        for (Order order : orders) {
            if (order.getIdOrder().equals(orderId)) {
                order.setStatus(OrderStatus.IN_DELIVERY);
            }
        }
    }
}
