package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.UserDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.User;

import java.util.List;

public class UserDAOMemory implements UserDAO {
    List<User> users;
    static{
        users.add(new "R1", "")
    }

    @Override
    public User getUser(User user) throws DAOException {
        return null;
    }
}
