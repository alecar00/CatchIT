package com.alessandro.caracciolo.catchit.query;

import com.alessandro.caracciolo.catchit.model.User;

import java.sql.*;

public class UserQuery {
    private UserQuery(){};

    public static ResultSet getUser(Connection conn, User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.LOGIN);

        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        return stmt.executeQuery();
    }
}
