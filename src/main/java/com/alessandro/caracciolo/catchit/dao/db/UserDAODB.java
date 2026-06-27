package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.UserDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.exceptions.InvalidRegistrationException;
import com.alessandro.caracciolo.catchit.model.Role;
import com.alessandro.caracciolo.catchit.model.User;
import com.alessandro.caracciolo.catchit.query.UserQuery;
import com.alessandro.caracciolo.catchit.singleton.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAODB implements UserDAO {
    Logger logger = Logger.getLogger(UserDAODB.class.getName());

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

    @Override
    public void insertRider(User user) throws DAOException {
        Connection conn = Connector.getConnection();

        try{
            conn.setAutoCommit(false);
            int success = UserQuery.insertRider(conn, user);

            if (success == 1) {
                conn.commit();
            }else{
                conn.rollback();
                throw new DAOException("Error during create new rider.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error during create new rider.");
        } finally {
            try{
                conn.setAutoCommit(true);
            }catch(SQLException e){
                logger.log(Level.SEVERE, "ATTENTION: Unable to reset autoCommit on connection", e);
            }
        }
    }

    @Override
    public void insertRestaurant(User user) throws DAOException {
        Connection conn = Connector.getConnection();

        try{
            conn.setAutoCommit(false);
            int success = UserQuery.insertRestaurant(conn, user);

            if (success == 1) {
                conn.commit();
            }else{
                conn.rollback();
                throw new DAOException("Error during create new restaurant.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error during create new restaurant.");
        } finally {
            try{
                conn.setAutoCommit(true);
            }catch(SQLException e){
                logger.log(Level.SEVERE, "ATTENTION: Unable to reset autoCommit on connection", e);
            }
        }
    }

    @Override
    public void saveUser(User user) throws DAOException, InvalidRegistrationException {

    }

   /* @Override
    public boolean checkUsername(String username) throws DAOException {

    }*/
}
