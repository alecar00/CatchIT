package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;

import java.util.logging.Logger;

public class LoginController {
    private static final Logger log = Logger.getLogger(LoginController.class.getName());

    public int verifyCredentials(UserBean userBean) throws DAOException, BusinessException {
        //not implemented
        return 1;//testing role restaurant
    }

}
