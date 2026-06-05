package com.alessandro.caracciolo.catchit.model;

import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.observer.Subject;

import java.io.Serializable;
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
        //not implemented
    }

    public void checkIfNotOutdated(Order orderFromDao) throws BusinessException {

        // 1. Controllo ID: Questo è un errore di programmazione, non di business.
        // Usiamo un'eccezione unchecked nativa di Java.
        if (!this.idOrder.equals(orderFromDao.getIdOrder())) {
            throw new IllegalArgumentException("Errore di sistema: impossibile confrontare ordini con ID diversi.");
        }

        // 2. Da qui in poi, se un dato è cambiato, lancio l'eccezione col dettaglio specifico
        if (this.status != orderFromDao.getStatus()) {
            throw new BusinessException("L'ordine è obsoleto: lo stato è cambiato in " + orderFromDao.getStatus());
        }

        if (!Objects.equals(this.address, orderFromDao.getAddress())) {
            throw new BusinessException("L'ordine è obsoleto: l'indirizzo di consegna è stato modificato.");
        }

        if (!Objects.equals(this.costumer, orderFromDao.getCostumer())) {
            throw new BusinessException("L'ordine è obsoleto: il nominativo del cliente è stato modificato.");
        }

        if (this.time != orderFromDao.getTime()) {
            throw new BusinessException("L'ordine è obsoleto: l'orario di consegna è cambiato.");
        }

        // 3. Controllo sul rider
        boolean sameRider = (this.rider == null && orderFromDao.getRider() == null) ||
                (this.rider != null && this.rider.equals(orderFromDao.getRider()));

        if (!sameRider) {
            throw new BusinessException("L'ordine è obsoleto: il rider assegnato è stato cambiato da un altro utente.");
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
