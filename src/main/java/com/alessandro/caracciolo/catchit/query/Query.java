package com.alessandro.caracciolo.catchit.query;

public class Query {
    private Query(){}

    public static final String GET_PENDING_ORDERS = "SELECT * FROM Orders WHERE status = 1";

    public static final String GET_AVAILABLE_RIDERS = "SELECT r.name " +
            "FROM Rider r " +
            "LEFT JOIN Orders o ON r.id_rider = o.id_rider " +
            "AND o.delivery_time = ? " +
            "AND o.status IN ('ASSIGNED', 'IN_DELIVERY') " +
            "WHERE o.id_order IS NULL; ";

}
