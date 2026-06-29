package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.exceptions.InvalidRegistrationException;
import com.alessandro.caracciolo.catchit.model.User;

public interface UserDAO {
    User getUserByUsername(String username) throws DAOException;
    void saveUser(User user) throws DAOException, InvalidRegistrationException;
    void deleteUser(String username) throws DAOException;
}