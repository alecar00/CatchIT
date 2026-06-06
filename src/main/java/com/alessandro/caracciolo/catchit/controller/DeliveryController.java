package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Rider;

import java.util.logging.Logger;

public class DeliveryController {
    private static final Logger log = Logger.getLogger(DeliveryController.class.getName());

    public void initialize() {
    }

    private void showOrdersRider(Rider rider) {
    }

    public void startDelivery(String idOrder) {
    }

    public void setOrderCompleted(String idOrder) throws DAOException, BusinessException {
        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        orderDAO.setOrderCompleted(idOrder);
    }
}