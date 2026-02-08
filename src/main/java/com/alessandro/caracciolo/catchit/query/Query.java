package com.alessandro.caracciolo.catchit.query;

public class Query {
    private Query(){}

    public static String GET_PENDING_ORDERS = "SELECT * FROM orders WHERE status = 1";

}
