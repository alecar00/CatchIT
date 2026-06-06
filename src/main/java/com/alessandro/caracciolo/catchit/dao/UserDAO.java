package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.User;

public interface UserDAO {
    User getUserByUsername(String username) throws DAOException;
}