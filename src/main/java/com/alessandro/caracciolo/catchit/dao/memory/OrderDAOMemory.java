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
        orders.add(new Order("2",
                "Piazza Dante 5",
                "Marco",
                "3339876543",
                Time.valueOf("19:30:00"),
                OrderStatus.PENDING));
        orders.add(new Order("3",
                "Piazza della repubblica 1",
                "Michael Jackson",
                "3338765432",
                Time.valueOf("20:30:00"),
                OrderStatus.PENDING));
        orders.add(new Order("4",
                "Silicon Valley",
                "Bill Gates",
                "3459876543",
                Time.valueOf("19:30:00"),
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
    public void assignOrder(Order orderBean, Rider rider) {
        for (Order order : orders) {
            if (order.getIdOrder().equals(orderBean.getIdOrder())) {
                order.setStatus(OrderStatus.ASSIGNED);
                order.setRider(rider);
            }
        }
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
    public List<Order> getOrdersByRider(String riderId) {
        List <Order> riderOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getRider() != null && order.getRider().getIdRider().equals(riderId)) {
                riderOrders.add(order);
            }
        }
        return riderOrders;
    }

    @Override
    public void setOrderInDelivery(String orderId) {
        for (Order order : orders) {
            if (order.getIdOrder().equals(orderId)) {
                order.setStatus(OrderStatus.IN_DELIVERY);
            }
        }
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }
}
