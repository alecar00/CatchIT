package com.alessandro.caracciolo.catchit.model;

import com.alessandro.caracciolo.catchit.Observer.Subject;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class Order extends Subject implements Serializable {
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

    @Override
    protected boolean isThereAnythingToNotify() {
        return false;
    }

    @Override
    protected void doSomething() {

    }

    public boolean isOutdatedComparedTo(Order orderFromDao) {
        // 1. Se gli ID sono diversi, stiamo parlando di due ordini diversi,
        // quindi non ha senso confrontarli.
        if (!this.idOrder.equals(orderFromDao.getIdOrder())) {
            return false;
        }

        // 2. Controllo se lo STATO è cambiato (es. da PENDING ad ASSIGNED)
        if (this.status != orderFromDao.getStatus()) {
            return true; // I dati sono cambiati!
        }

        if(!Objects.equals(this.address, orderFromDao.getAddress())) {
            return true;
        }

        if(!Objects.equals(this.costumer, orderFromDao.getCostumer())) {
            return true;
        }

        if(this.time != orderFromDao.getTime()) {
            return true;
        }

        // 3. Controllo se il RIDER è cambiato
        boolean sameRider = (this.rider == null && orderFromDao.getRider() == null) ||
                (this.rider != null && this.rider.equals(orderFromDao.getRider()));

        if (!sameRider) {
            return true;
        }

        return false;
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

    public String getTelNumber() {return this.telNumber;}
}
