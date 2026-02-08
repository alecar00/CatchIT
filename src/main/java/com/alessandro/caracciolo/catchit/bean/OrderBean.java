package com.alessandro.caracciolo.catchit.bean;

public class OrderBean {
    private String idOrder;
    private String address;
    private String costumer;
    private String telNumber;
    private String rider;
    private String time;
    private String date;
    private String status;

    public OrderBean(String idOrder, String address, String costumer, String telNumber, String rider, String time, String date, String status) {
        this.idOrder = idOrder;
        this.address = address;
        this.costumer = costumer;
        this.telNumber = telNumber;
        this.rider = rider;
        this.time = time;
        this.date = date;
        this.status = status;
    }

    // Getters Setters
    public String getIdOrder() {return idOrder;}
    public String getAddress() {return address;}
    public String getConsumer() {return costumer;}
    public String getStatus() {return this.status;}
    public String getTelNumber() {return this.telNumber;}
    public String getRider() {return this.rider;}
    public String getTime() {return this.time;}
    public String getDate() {return this.date;}


}
