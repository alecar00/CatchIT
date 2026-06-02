package com.alessandro.caracciolo.catchit.query;

import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;

import java.sql.*;

public class RiderQuery {
    public static int assignRider(Connection conn, Order order, Rider rider) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.ASSIGN_RIDER);

        stmt.setString(1, rider.getIdRider());
        stmt.setString(2, order.getIdOrder());

        return stmt.executeUpdate();
    }

    public static ResultSet getAvailableRiders(Connection conn, Time time) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_AVAILABLE_RIDERS);

        stmt.setString(1, time.toString());

        return stmt.executeQuery();
    }

    public static ResultSet getRiderById(Connection conn, String id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_RIDER_BY_ID);

        stmt.setString(1, id);
        return stmt.executeQuery();
    }
}