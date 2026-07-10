package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.UserDAO;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.User;
import com.alessandro.caracciolo.catchit.singleton.Configs;

import java.util.logging.Logger;

public class LoginController {
    Logger logger = Logger.getLogger(Configs.LOGGER_NAME);

    public int verifyCredentials(UserBean userBean) throws DAOException, BusinessException {

        UserDAO userDAO = DAOFactory.getDAOFactory().createUserDAO();

        User user = userDAO.getUserByUsername(userBean.getUsername());

        if (user == null) {
            throw new BusinessException("Incorrect credentials.");
        }

        if (!user.getPassword().equals(userBean.getPassword())) {
            throw new BusinessException("Incorrect credentials.");
        }

        logger.info("User " + userBean.getUsername() + " is logged in.");

        return user.getRole().getId();
    }
}