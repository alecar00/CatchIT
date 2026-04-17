package com.alessandro.caracciolo.catchit.query;

public class Query {
    private Query(){}

    public static final String GET_PENDING_ORDERS = "SELECT * FROM Orders WHERE status = 1";

}
