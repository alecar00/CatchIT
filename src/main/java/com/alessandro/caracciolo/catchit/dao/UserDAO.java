package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.User;

public interface UserDAO {
    public User getUser(User user) throws DAOException;
}
