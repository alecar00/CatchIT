package com.alessandro.caracciolo.catchit.bean;

public class RiderBean {
    private final String idRider;
    private String name;
    private Boolean permitZTL;

    public RiderBean(String idRider, String name, Boolean permitZTL) {
        this.idRider = idRider;
        this.name = name;
        this.permitZTL = permitZTL;
    }

    public RiderBean(String idRider, String name) {
        this.idRider = idRider;
        this.name = name;
    }

    //getter setter
    public String getIdRider() {return idRider;}
    public String getName() {return name;}
    public Boolean getPermitZTL() {return permitZTL;}

}
