package com.alessandro.caracciolo.catchit.model;

import java.sql.Date;
import java.sql.Time;

public class Order {
    String idOrder;
    String address;
    Rider rider;
    Restaurant restaurant;
    Time time;
    Date date;

    public Order(String idOrder, String address,Rider rider, Restaurant restaurant, Time time, Date date) {
        this.idOrder = idOrder;
        this.address = address;
        this.rider = rider;
        this.restaurant = restaurant;
        this.time = time;
        this.date = date;
    }


}
