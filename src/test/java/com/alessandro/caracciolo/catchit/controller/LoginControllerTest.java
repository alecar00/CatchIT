package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginControllerTest {

    private LoginController loginController;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();
    }

    @Test
    void testVerifyCredentialsSuccess() throws DAOException, BusinessException {
        UserBean userBean = new UserBean("DaAlessandro", "admin");
        int roleId = loginController.verifyCredentials(userBean);

        assertEquals(1, roleId);
    }

    @Test
    void testVerifyCredentialsWrongPassword() {
        UserBean userBean = new UserBean("DaAlessandro", "wrongpass");

        assertThrows(BusinessException.class, () -> {
            loginController.verifyCredentials(userBean);
        });
    }

    @Test
    void testVerifyCredentialsUserNotFound() {
        UserBean userBean = new UserBean("UtenteInesistente", "password");

        assertThrows(BusinessException.class, () -> {
            loginController.verifyCredentials(userBean);
        });
    }
}