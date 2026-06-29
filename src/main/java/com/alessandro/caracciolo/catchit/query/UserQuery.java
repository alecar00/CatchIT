package com.alessandro.caracciolo.catchit.query;

import com.alessandro.caracciolo.catchit.model.User;

import java.sql.*;

public class UserQuery {
    private UserQuery(){}

    public static ResultSet getUserByUsername(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_USER_BY_USERNAME);
        stmt.setString(1, username);
        return stmt.executeQuery();
    }

    public static int insertRestaurant(Connection conn, User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.INSERT_RESTAURANT_USER);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        return stmt.executeUpdate();
    }

    public static int insertRider(Connection conn, User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.INSERT_RIDER_USER);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        return stmt.executeUpdate();
    }

    public static boolean checkUsername(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.CHECK_USERNAME);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }
}
