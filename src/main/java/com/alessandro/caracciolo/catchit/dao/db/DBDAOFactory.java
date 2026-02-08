package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.dao.RestaurantDAO;
import com.alessandro.caracciolo.catchit.dao.RiderDAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBDAOFactory extends DAOFactory {

    @Override
    public OrderDAO createOrderDAO() {
        return new OrderDAODB();
    }

    @Override
    public RiderDAO createRiderDAO() {
        return new RiderDAODB();
    }

    @Override
    public RestaurantDAO createRestaurantDAO() {
        return null;
    }
}
