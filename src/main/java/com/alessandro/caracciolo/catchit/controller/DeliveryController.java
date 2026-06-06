package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.OrderStatus;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DeliveryController {
    private static final Logger log = Logger.getLogger(DeliveryController.class.getName());


    public List<OrderBean> showOrdersRider(String riderId) throws DAOException {
        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        return orderDAO.getOrdersByRider(riderId).stream()
                .map(o -> new OrderBean(o.getIdOrder(), o.getAddress(), o.getCostumer(), o.getTelNumber(), o.getTime(), o.getStatus()))
                .collect(Collectors.toList());
    }

    public void startDelivery(String idOrder) throws BusinessException, DAOException {
        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        Order order = orderDAO.getOrderById(idOrder);

        if (order == null) {
            throw new BusinessException("Order #" + idOrder + " not found.");
        }

        if (order.getStatus() != OrderStatus.ASSIGNED) {
            throw new BusinessException("Order #" + idOrder + " cannot be started. Status must be ASSIGNED.");
        }

        order.setStatus(OrderStatus.IN_DELIVERY);
        orderDAO.setOrderInDelivery(idOrder);
    }

    public void setOrderCompleted(String idOrder) throws DAOException, BusinessException {
        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        orderDAO.setOrderCompleted(idOrder);
    }
}