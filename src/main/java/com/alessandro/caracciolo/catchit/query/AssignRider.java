package com.alessandro.caracciolo.catchit.query;

import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;

import java.sql.*;

public class AssignRider {
    public static int assignRider(Connection conn, Order order, Rider rider) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.ASSIGN_RIDER);

        stmt.setString(1, rider.getIdRider());
        stmt.setString(2, order.getIdOrder());

        return stmt.executeUpdate();
    }
}