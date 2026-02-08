package com.alessandro.caracciolo.catchit.model;

import com.alessandro.caracciolo.catchit.Observer.Subject;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Order extends Subject implements Serializable {
    private String idOrder;
    private String address;
    private String costumer;
    private String telNumber;
    private Rider rider;
    private Time time;
    private OrderStatus status;

    public Order(String idOrder, String address, String costumer,String telNumber, Time time, Date date, OrderStatus status) {
        this.idOrder = idOrder;
        this.address = address;
        this.costumer = costumer;
        this.telNumber = telNumber;
        this.rider = null;
        this.time = time;
        this.status = OrderStatus.PENDING;
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
