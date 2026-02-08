package com.alessandro.caracciolo.catchit.bean;

import com.alessandro.caracciolo.catchit.model.OrderStatus;

import java.sql.Time;

public class OrderBean {
    private String idOrder;
    private String address;
    private String costumer;
    private String telNumber;
    private RiderBean rider;
    private Time time;
    private OrderStatus status;

    public OrderBean(String idOrder, String address, String costumer, String telNumber, RiderBean rider, Time time, OrderStatus status) {
        this.idOrder = idOrder;
        this.address = address;
        this.costumer = costumer;
        this.telNumber = telNumber;
        this.rider = rider;
        this.time = time;
        this.status = status;
    }

    // Getters Setters
    public String getIdOrder() {return idOrder;}
    public String getAddress() {return address;}
    public String getConsumer() {return costumer;}
    public OrderStatus getStatus() {return this.status;}
    public String getTelNumber() {return this.telNumber;}
    public RiderBean getRider() {return this.rider;}
    public Time getTime() {return this.time;}


}
