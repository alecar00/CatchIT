package com.alessandro.caracciolo.catchit.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static Connection conn = null;
    private static final String DB_URL = Configs.getInstance().getProperty("CONNECTION_URL");
    private static final String DB_USER = Configs.getInstance().getProperty("RESTAURANT_USER");
    private static final String DB_PSSW = Configs.getInstance().getProperty("RESTAURANT_PSSW");

    private Connector(){}

    public static synchronized Connection getConnection(){
        try {
            if (conn == null) {
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PSSW);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

}
