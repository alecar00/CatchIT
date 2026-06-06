package com.alessandro.caracciolo.catchit.model;

import com.alessandro.caracciolo.catchit.exceptions.BusinessException;

import java.io.Serializable;
import java.sql.Time;
import java.util.Objects;

public class Order implements Serializable {
    private String idOrder;
    private String address;
    private String costumer;
    private String telNumber;
    private Rider rider;
    private Time time;
    private OrderStatus status;
    private Restaurant restaurant;

    public Order(String idOrder, String address, String costumer,String telNumber, Time time, OrderStatus status) {
        this.idOrder = idOrder;
        this.address = address;
        this.costumer = costumer;
        this.telNumber = telNumber;
        this.rider = null;
        this.time = time;
        this.status = status;
        this.restaurant = null;
    }

    public Order(String idOrder, String address, String costumer,String telNumber,Rider rider, Time time, OrderStatus status) {
        this.idOrder = idOrder;
        this.address = address;
        this.costumer = costumer;
        this.telNumber = telNumber;
        this.rider = rider;
        this.time = time;
        this.status = status;
        this.restaurant = null;
    }


    public OrderStatus getStatus() {
        return this.status;
    }

    public void checkIfNotOutdated(Order orderFromDao) throws BusinessException {
        if (!this.idOrder.equals(orderFromDao.getIdOrder())) {
            throw new IllegalArgumentException("System error: unable to compare orders with different IDs.");
        }

        if (this.status != orderFromDao.getStatus()) {
            throw new BusinessException("The order is outdated: the status has changed to " + orderFromDao.getStatus());
        }

        if (!Objects.equals(this.address, orderFromDao.getAddress())) {
            throw new BusinessException("The order is outdated: the delivery address has been modified.");
        }

        if (!Objects.equals(this.costumer, orderFromDao.getCostumer())) {
            throw new BusinessException("The order is outdated: the customer name has been modified.");
        }

        if (!Objects.equals(this.time, orderFromDao.getTime())) {
            throw new BusinessException("The order is outdated: the delivery time has changed.");
        }

        boolean sameRider = false;
        if (this.rider == null && orderFromDao.getRider() == null) {
            sameRider = true;
        } else if (this.rider != null && orderFromDao.getRider() != null) {
            sameRider = this.rider.getIdRider().equals(orderFromDao.getRider().getIdRider());
        }

        if (!sameRider) {
            throw new BusinessException("The order is outdated: the assigned rider has been changed by another user.");
        }
    }

    public String getAddress() {
        return this.address;
    }

    public String getCostumer() {
        return this.costumer;
    }

    public String getIdOrder() {
        return this.idOrder;
    }

    public Rider getRider() {
        return this.rider;
    }

    public Time getTime() {
        return this.time;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getTelNumber() {return this.telNumber;}
}
