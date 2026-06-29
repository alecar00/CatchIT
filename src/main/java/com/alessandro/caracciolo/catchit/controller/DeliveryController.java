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
import com.alessandro.caracciolo.catchit.singleton.Configs;

import java.util.List;
import java.util.logging.Logger;

public class DeliveryController {
    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);
    private static final String ERROR_ORDER = "Error: Order #";

    public List<OrderBean> showOrdersRider(String riderId) throws DAOException {
        logger.info(() -> "Getting orders assigned to rider: " + riderId);
        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        return orderDAO.getOrdersByRider(riderId).stream()
                .map(o -> new OrderBean(o.getIdOrder(), o.getAddress(), o.getCostumer(), o.getTelNumber(), o.getTime(), o.getStatus()))
                .toList();
    }

    public void startDelivery(String idOrder, String idRider) throws BusinessException, DAOException {
        logger.info(() -> "Attempting to start delivery. OrderID: " + idOrder + ", RiderID: " + idRider);
        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        Order order = orderDAO.getOrderById(idOrder);

        RiderDAO riderDAO = DAOFactory.getDAOFactory().createRiderDAO();
        Rider rider = riderDAO.getRiderById(idRider);

        if (order == null) {
            logger.warning(() ->ERROR_ORDER + idOrder + " not found.");
            throw new BusinessException("Order #" + idOrder + " not found.");
        }

        if (order.getStatus() != OrderStatus.ASSIGNED) {
            logger.warning(() ->ERROR_ORDER + idOrder + " has invalid status: " + order.getStatus());
            throw new BusinessException("Order #" + idOrder + " cannot be started. Status must be ASSIGNED.");
        }

        /*
         * Check if the order has an assigned rider
         * to prevent a NullPointerException during equality evaluation.
         */
        if (order.getRider() == null) {
            logger.warning(() -> ERROR_ORDER + idOrder + " has no rider assigned.");
            throw new BusinessException("This order has no rider assigned.");
        }

        if (rider == null) {
            logger.severe(() -> "Error: Rider lookup returned null for ID: " + idRider);
            throw new BusinessException("System error: Rider record not found.");
        }

        if(!order.getRider().getIdRider().equals(rider.getIdRider())){
            logger.warning(() -> "Security alert: Rider " + idRider + " tried to start delivery for order " + idOrder + " assigned to " + order.getRider().getIdRider());
            throw new BusinessException("This order is assigned to another rider.");
        }

        order.setStatus(OrderStatus.IN_DELIVERY);
        orderDAO.setOrderInDelivery(idOrder);
        logger.info(() -> "Delivery successfully started for Order #" + idOrder + " by Rider " + idRider);
    }

    public void setOrderCompleted(String idOrder) throws DAOException {
        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        orderDAO.setOrderCompleted(idOrder);
    }
}