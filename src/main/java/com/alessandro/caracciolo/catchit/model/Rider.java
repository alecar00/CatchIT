package com.alessandro.caracciolo.catchit.model;

import java.io.Serializable;

public class Rider implements Serializable {
    private String idRider;
    private String name;
    private boolean permitZTL;

    public Rider (String idRider, String name,  boolean permitZTL) {
        this.idRider = idRider;
        this.name = name;
        this.permitZTL = permitZTL;
    }
    public String getIdRider() { return idRider; }
    public void setIdRider(String idRider) { this.idRider = idRider; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isPermitZTL() {
        return permitZTL;
    }

    public void setPermitZTLZTL(boolean permitZTL) {
        this.permitZTL = permitZTL;
    }
}


