package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.dao.UserDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.exceptions.InvalidRegistrationException;
import com.alessandro.caracciolo.catchit.exceptions.UsernameAlreadyUsed;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.model.Role;
import com.alessandro.caracciolo.catchit.model.User;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.logging.Logger;

public class RegisterController {
    Logger logger = Logger.getLogger(Configs.LOGGER_NAME);
    private void registerUser(UserBean userBean) throws DAOException, UsernameAlreadyUsed, InvalidRegistrationException {
        validateFields(userBean.getUsername(),  userBean.getPassword());

        Role role = Role.valueOf(userBean.getRole());
        UserDAO userDAO = DAOFactory.getDAOFactory().createUserDAO();
        User user = new User(userBean.getUsername(),
                userBean.getPassword(),
                role);
        User userFromDao = null;

        try{
            userFromDao = userDAO.getUserByUsername(userBean.getUsername());
        } catch(DAOException e){
            logger.severe("Error in checking if user exists");
        }

        if(userFromDao != null){
            throw new UsernameAlreadyUsed();
        }

        try {
            if (role == Role.RESTAURANT || role == Role.RIDER) {
                userDAO.saveUser(user);
            } else {
                logger.severe("ERROR: unknown role: " + role);
                throw new InvalidRegistrationException("Unable to retrieve user role.");
            }
        }catch(DAOException e){
            logger.severe("Error in registerUser: " + e.getMessage());
            Printer.errorPrint("Error during registration.");
            throw new DAOException();
        }

    }

    public void validateFields(String username, String password) throws InvalidRegistrationException {
        if (username == null || username.isBlank()) {
            throw new InvalidRegistrationException("Username cannot be empty!");
        }
        if (username.contains(" ")){
            throw new InvalidRegistrationException("Username cannot contain spaces!");
        }
        if (username.length() < 2) {
            throw new InvalidRegistrationException("Username must be at least 2 characters!");
        }
        if (password == null || password.isBlank()) {
            throw new InvalidRegistrationException("Password cannot be empty!");
        }
        if (password.length() < 5) {
            throw new InvalidRegistrationException("Password must be at least 5 characters!");
        }
        if (password.contains(" ")){
            throw new InvalidRegistrationException("Password cannot contain spaces!");
        }
    }

    public void startRiderRegistration(UserBean userBean, RiderBean riderBean) throws InvalidRegistrationException, UsernameAlreadyUsed, DAOException {
        if (riderBean.getName() == null || riderBean.getName().isBlank()) {
            throw new InvalidRegistrationException("Rider name cannot be empty!");
        }

        registerUser(userBean);

        Rider rider = new Rider(riderBean.getIdRider(), riderBean.getName());
        RiderDAO riderDAO = DAOFactory.getDAOFactory().createRiderDAO();

        try {
            riderDAO.saveRider(rider);

        } catch (DAOException _) {
            logger.warning(() -> "Errore in saveRider, avvio rollback per l'utente: " + userBean.getUsername());

            UserDAO userDAO = DAOFactory.getDAOFactory().createUserDAO();
            try {
                userDAO.deleteUser(userBean.getUsername());
            } catch (DAOException e) {
                logger.severe(() -> "ERRORE CRITICO: Impossibile eseguire il rollback dell'utente " + userBean.getUsername() + ". Il database potrebbe essere inconsistente.");
                throw new DAOException("Error during creation of the Rider. Registration canceled.", e);
            }
        }
    }

    public void startRestaurantRegistration(UserBean userBean) throws InvalidRegistrationException, UsernameAlreadyUsed, DAOException {
        registerUser(userBean);
    }
}
