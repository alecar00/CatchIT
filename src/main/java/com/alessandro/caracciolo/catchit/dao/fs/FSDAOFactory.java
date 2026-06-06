package com.alessandro.caracciolo.catchit.dao.fs;

import com.alessandro.caracciolo.catchit.dao.*;

public class FSDAOFactory extends DAOFactory {
    @Override
    public OrderDAO createOrderDAO() {
        return new OrderDAOFS();
    }

    @Override
    public RiderDAO createRiderDAO() {
        return new  RiderDAOFS();
    }

    @Override
    public RestaurantDAO createRestaurantDAO() {
        return null;
    }

    @Override
    public UserDAO createUserDAO() {
        return new UserDAOFS();
    }
}
