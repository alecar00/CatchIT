package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.dao.RestaurantDAO;
import com.alessandro.caracciolo.catchit.dao.RiderDAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBDAOFactory extends DAOFactory {

    @Override
    public OrderDAO getOrderDAO() {
        return null;
    }

    @Override
    public RiderDAO getRiderDAO() {
        return null;
    }

    @Override
    public RestaurantDAO getRestaurantDAO() {
        return null;
    }
}
