package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.UserDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Role;
import com.alessandro.caracciolo.catchit.model.User;
import com.alessandro.caracciolo.catchit.query.UserQuery;
import com.alessandro.caracciolo.catchit.singleton.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAODB implements UserDAO {
    @Override
    public User getUserByUsername(String username) throws DAOException {
        Connection conn = Connector.getConnection();

        try (ResultSet rs = UserQuery.getUserByUsername(conn, username)) {
            if (rs.next()) {
                String pwd = rs.getString("password");
                String roleStr = rs.getString("role");

                Role role = Role.valueOf(roleStr.toUpperCase());

                return new User(username, pwd, role);
            }
        } catch (SQLException e) {
            throw new DAOException("Errore critico durante il recupero dell'utente dal database.", e);
        }

        return null;
    }
}
