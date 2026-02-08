package com.alessandro.caracciolo.catchit.dao.fs;

import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.dao.RestaurantDAO;
import com.alessandro.caracciolo.catchit.dao.RiderDAO;

public class FSDAOFactory extends DAOFactory {
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
