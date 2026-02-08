package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.dao.db.DBDAOFactory;
import com.alessandro.caracciolo.catchit.dao.fs.FSDAOFactory;
import com.alessandro.caracciolo.catchit.dao.memory.MemoryDAOFactory;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DAOFactory {

    // --- PATTERN SINGLETON ---
    private static DAOFactory instance = null;

    protected DAOFactory() {
    }

    /**
     * Metodo statico che fa da "Factory Creator" e da "Singleton Access Point".
     * @return L'unica istanza attiva della Factory corretta.
     */
    public static synchronized DAOFactory getDAOFactory() {
        if (instance == null) {
            Properties properties = new Properties();

            try (InputStream input = DAOFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
                properties.load(input);
            } catch (IOException e){
                Printer.errorPrint(e.getMessage());
            }

            String persistenceType = properties.getProperty("PERSISTENCE_TYPE");

            switch (persistenceType.toUpperCase()) {
                case "FS":
                    instance = new FSDAOFactory();
                    break;
                case "DEMO":
                    instance = new MemoryDAOFactory();
                    break;
                case "DB":
                default:
                    instance = new DBDAOFactory();
            }
        }
        return instance;
    }

    // --- METODI ASTRATTI  ---
    // Le sottoclassi DEVONO implementare questi metodi per restituire i DAO specifici.
    public abstract OrderDAO createOrderDAO();

    public abstract RiderDAO createRiderDAO();

    public abstract RestaurantDAO createRestaurantDAO();
}
