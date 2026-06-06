package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.UserDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.exceptions.UserNotFoundException;
import com.alessandro.caracciolo.catchit.model.Role;
import com.alessandro.caracciolo.catchit.model.User;
import com.alessandro.caracciolo.catchit.query.UserQuery;
import com.alessandro.caracciolo.catchit.singleton.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAODB implements UserDAO {
    @Override
    public User getUser(User user) throws DAOException{
        try{
            ResultSet rs = UserQuery.getUser(Connector.getConnection(), user);
            if (rs.next()) {
                return new User(rs.getString(
                        "username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")));
            }else{
                throw new UserNotFoundException();
            }
        }catch(SQLException e){
            throw new DAOException(e.getMessage());
        }

    }
}
