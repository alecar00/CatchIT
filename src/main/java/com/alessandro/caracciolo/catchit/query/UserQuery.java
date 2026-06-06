package com.alessandro.caracciolo.catchit.query;

import com.alessandro.caracciolo.catchit.model.User;

import java.sql.*;

public class UserQuery {
    private UserQuery(){};

    public static ResultSet getUserByUsername(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_USER_BY_USERNAME);
        stmt.setString(1, username);
        return stmt.executeQuery();
    }
}
