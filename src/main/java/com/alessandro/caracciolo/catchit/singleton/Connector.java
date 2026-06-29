package com.alessandro.caracciolo.catchit.singleton;

import com.alessandro.caracciolo.catchit.exceptions.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static Connection conn = null;
    private static final String DB_URL = Configs.getInstance().getProperty("CONNECTION_URL");
    private static final String DB_USER = Configs.getInstance().getProperty("LOGIN_USER");
    private static final String DB_PSSW = Configs.getInstance().getProperty("LOGIN_PASS");

    private Connector(){}

    public static synchronized Connection getConnection() throws DAOException {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PSSW);
            }
        }catch(SQLException e){
            throw new DAOException("Impossibile connettersi al db!", e);
        }
        return conn;
    }

}
