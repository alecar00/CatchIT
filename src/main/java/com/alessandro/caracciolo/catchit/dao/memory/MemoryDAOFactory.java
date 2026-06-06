package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.*;

public class MemoryDAOFactory extends DAOFactory {
    @Override
    public OrderDAO createOrderDAO() {
        return new OrderDAOMemory();
    }

    @Override
    public RiderDAO createRiderDAO() {
        return new  RiderDAOMemory();
    }

    @Override
    public UserDAO createUserDAO() {
        return new UserDAOMemory(); // <-- INIETTIAMO LA LOGICA IN MEMORIA
    }
}
