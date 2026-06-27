package com.alessandro.caracciolo.catchit.dao;

import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.exceptions.InvalidRegistrationException;
import com.alessandro.caracciolo.catchit.model.User;

public interface UserDAO {
    User getUserByUsername(String username) throws DAOException;
    void insertRider(User user) throws DAOException, InvalidRegistrationException;
    void insertRestaurant(User user) throws DAOException, InvalidRegistrationException;
    void saveUser(User user) throws DAOException, InvalidRegistrationException;
    //boolean checkUsername(String username) throws DAOException;
}