package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.OrderStatus;
import com.alessandro.caracciolo.catchit.model.Rider;

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

    public void startDelivery(String idOrder, String idRider) throws BusinessException, DAOException {
        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        Order order = orderDAO.getOrderById(idOrder);

        RiderDAO riderDAO = DAOFactory.getDAOFactory().createRiderDAO();
        Rider rider = riderDAO.getRiderById(idRider);

        if (order == null) {
            throw new BusinessException("Order #" + idOrder + " not found.");
        }

        if (order.getStatus() != OrderStatus.ASSIGNED) {
            throw new BusinessException("Order #" + idOrder + " cannot be started. Status must be ASSIGNED.");
        }

        /*
         * Check if the order has an assigned rider
         * to prevent a NullPointerException during equality evaluation.
         */
        if (order.getRider() == null) {
            throw new BusinessException("This order has no rider assigned.");
        }

        if(!order.getRider().getIdRider().equals(rider.getIdRider())){
            throw new BusinessException("This order is assigned to another rider.");
        }

        order.setStatus(OrderStatus.IN_DELIVERY);
        orderDAO.setOrderInDelivery(idOrder);
    }

    public void setOrderCompleted(String idOrder) throws DAOException, BusinessException {
        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        orderDAO.setOrderCompleted(idOrder);
    }
}