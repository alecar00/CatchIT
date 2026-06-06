package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.UserDAO;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.User;

public class LoginController {

    public int verifyCredentials(UserBean userBean) throws DAOException, BusinessException {

        UserDAO userDAO = DAOFactory.getDAOFactory().createUserDAO();

        User user = userDAO.getUserByUsername(userBean.getUsername());

        if (user == null) {
            throw new BusinessException("Username not found in the system.");
        }

        if (!user.getPassword().equals(userBean.getPassword())) {
            throw new BusinessException("Incorrect password.");
        }

        //Se tutto è corretto, restituiamo l'ID del ruolo (1=Ristorante, 2=Rider)
        return user.getRole().getId();
    }
}