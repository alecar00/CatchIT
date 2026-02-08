package com.alessandro.caracciolo.catchit.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionRequest {
    private static Connection conn;

    public static void startConnection(){
        startConnection("LOGIN", "LOGIN");
    }

    public static void startConnection(String username, String password) {
        //It can be tried to connect with a specific user
        try (InputStream input = ConnectionRequest.class.getResourceAsStream("/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String connection_url = properties.getProperty("CONNECTION_URL");
            String user = properties.getProperty(username + "_USER");
            String pass = properties.getProperty(password + "_PASS");

            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(connection_url, user, pass);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return conn;
    }

/*    public static void changeRole(Role role) throws SQLException {
        conn.close();
        startConnection(role.name(),role.name());
    }*/
}
