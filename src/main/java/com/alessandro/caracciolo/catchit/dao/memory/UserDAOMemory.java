package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.UserDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.exceptions.InvalidRegistrationException;
import com.alessandro.caracciolo.catchit.model.Role;
import com.alessandro.caracciolo.catchit.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAOMemory implements UserDAO {

    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User("DaAlessandro", "admin", Role.RESTAURANT));

        users.add(new User("R1", "password", Role.RIDER));
        users.add(new User("R2", "password", Role.RIDER));
    }

    @Override
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }



    @Override
    public void insertRider(User user) throws DAOException, InvalidRegistrationException {
        saveUser(user);
    }

    @Override
    public void insertRestaurant(User user) throws DAOException, InvalidRegistrationException {
        saveUser(user);
    }

    @Override
    public void saveUser(User user) throws DAOException, InvalidRegistrationException {
        users.add(user);
    }

    /*@Override
    public void checkUsername(String username) throws DAOException {

    }*/

}