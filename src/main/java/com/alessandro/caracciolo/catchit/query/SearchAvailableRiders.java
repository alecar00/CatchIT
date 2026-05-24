package com.alessandro.caracciolo.catchit.query;

import com.alessandro.caracciolo.catchit.model.Order;

import java.sql.*;


public class SearchAvailableRiders {
    public static ResultSet getAvailableRiders(Connection conn, Time time) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_AVAILABLE_RIDERS);

        stmt.setString(1, time.toString());

        return stmt.executeQuery();
    }
}

