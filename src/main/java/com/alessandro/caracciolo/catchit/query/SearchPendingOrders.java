package com.alessandro.caracciolo.catchit.query;

import com.alessandro.caracciolo.catchit.model.OrderStatus;

import java.sql.*;

public class SearchPendingOrders {
    public static ResultSet SearchOrdersByStatus(Connection conn, OrderStatus orderStatus) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM orders WHERE status = ?");
        stmt.setString(1, orderStatus.toString());
        return stmt.executeQuery();

    }
}
