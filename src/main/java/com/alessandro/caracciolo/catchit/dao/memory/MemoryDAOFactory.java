package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.dao.RestaurantDAO;
import com.alessandro.caracciolo.catchit.dao.RiderDAO;

public class MemoryDAOFactory extends DAOFactory {
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
