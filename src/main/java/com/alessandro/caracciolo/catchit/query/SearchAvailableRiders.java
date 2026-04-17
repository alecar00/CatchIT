package com.alessandro.caracciolo.catchit.query;

import com.alessandro.caracciolo.catchit.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchAvailableRiders {
    public static ResultSet getAvailableRiders(Connection conn, Order order) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_AVAILABLE_RIDERS);
        return stmt.executeQuery();

    }
}

