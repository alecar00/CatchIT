package com.alessandro.caracciolo.catchit.query;

public class Query {
    private Query(){}

    public static final String GET_PENDING_ORDERS = "SELECT * FROM Orders WHERE status = ?";

    public static final String GET_AVAILABLE_RIDERS = "SELECT r.id_rider, r.name, r.permit_ztl " +
            "FROM Rider r " +
            "LEFT JOIN Orders o ON r.id_rider = o.id_rider " +
            "AND o.delivery_time = ? " +
            "AND o.status IN ('ASSIGNED', 'IN_DELIVERY') " +
            "WHERE o.id_order IS NULL; ";

    public static final String ASSIGN_RIDER = "UPDATE Orders SET id_rider = ?, status = 'ASSIGNED' WHERE id_order = ?;";

    public static final String GET_ORDER_BY_ID = "SELECT * FROM Orders WHERE id_order = ?;";

    public static final String GET_RIDER_BY_ID = "SELECT * FROM Rider WHERE id_rider = ?;";
}
