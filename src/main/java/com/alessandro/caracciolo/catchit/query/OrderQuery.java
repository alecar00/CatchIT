package com.alessandro.caracciolo.catchit.query;

import com.alessandro.caracciolo.catchit.model.OrderStatus;

import java.sql.*;

public class OrderQuery {

    private OrderQuery() {}
    public static ResultSet getPendingOrders(Connection conn, OrderStatus orderStatus) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_PENDING_ORDERS);
        stmt.setString(1, orderStatus.toString());
        return stmt.executeQuery();

    }

    public static ResultSet getOrderById(Connection conn, String orderId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_ORDER_BY_ID);
        stmt.setString(1, orderId);
        return stmt.executeQuery();
    }

    public static int setOrderCompleted(Connection conn, String orderId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.SET_ORDER_COMPLETED);
        stmt.setString(1, orderId);
        return stmt.executeUpdate();
    }
}
