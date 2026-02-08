package com.alessandro.caracciolo.catchit.model;

import com.alessandro.caracciolo.catchit.bean.OrderBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private List<Rider> riders;
    private List<Order> orders;

    public Restaurant(String name) {
        this.name = name;
        this.riders = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public List<OrderBean> getPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>();
        List<OrderBean> orderBeans = new ArrayList<>();

        //implementare chiamata dao

        return orderBeans;
    }

    public List<Order> getRiderOrders(Rider rider) {
        List<Order> result = new ArrayList<>();
        for (Order o : this.orders) {
            if (o.getRider() != null && o.getRider().getIdRider().equals(rider.getIdRider())) {
                result.add(o);
            }
        }
        return result;
    }

    public Order getOrder(String idOrder) {
        for (Order order : orders) {
            if (order.getIdOrder().equals(idOrder)) return order;
        }
        return null;
    }

    public Rider getRider(String idRider) {
        for (Rider r : riders) {
            if (r.getIdRider().equals(idRider)) return r;
        }
        return null;
    }

    public void addOrder(Order o) { orders.add(o); }
    public void addRider(Rider r) { riders.add(r); }
}
