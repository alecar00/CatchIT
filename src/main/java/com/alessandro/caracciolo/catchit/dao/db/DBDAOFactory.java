package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.*;


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

    @Override
    public UserDAO createUserDAO() {
        return new UserDAODB();
    }
}
