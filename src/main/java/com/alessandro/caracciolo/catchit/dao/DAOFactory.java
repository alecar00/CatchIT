package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.dao.db.DBDAOFactory;
import com.alessandro.caracciolo.catchit.dao.fs.FSDAOFactory;
import com.alessandro.caracciolo.catchit.dao.memory.MemoryDAOFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DAOFactory {

    // --- PATTERN SINGLETON ---
    private static DAOFactory instance = null;

    protected DAOFactory() {
    }

    /**
     * Metodo statico che fa da "Factory Creator" e da "Singleton".
     * @return L'unica istanza attiva della Factory corretta.
     */
    public static DAOFactory getDAOFactory() {
        synchronized (DAOFactory.class) {
            if (instance == null) {
                Properties properties = new Properties();

                try (InputStream input = DAOFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
                    if (input == null) {
                        throw new IllegalStateException("Critical configuration missing: unable to find config.properties");
                    }
                    properties.load(input);
                } catch (IOException e) {
                    throw new IllegalStateException("Fatal error while reading config.properties", e);
                }

                String persistenceType = properties.getProperty("PERSISTENCE_TYPE");

                if (persistenceType == null || persistenceType.trim().isEmpty()) {
                    throw new IllegalStateException("The PERSISTENCE_TYPE property is not defined in the config.properties file");
                }

                switch (persistenceType.trim().toUpperCase()) {
                    case "FS":
                        instance = new FSDAOFactory();
                        break;
                    case "DEMO":
                        instance = new MemoryDAOFactory();
                        break;
                    case "DB":
                        instance = new DBDAOFactory();
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid PERSISTENCE_TYPE value: '" + persistenceType + "'.Expected values: FS, DEMO, DB.");
                }
            }
        }
        return instance;
    }

    // --- METODI ASTRATTI  ---
    // Le sottoclassi DEVONO implementare questi metodi per restituire i DAO specifici.
    public abstract OrderDAO createOrderDAO();

    public abstract RiderDAO createRiderDAO();

    public abstract UserDAO createUserDAO();
}
