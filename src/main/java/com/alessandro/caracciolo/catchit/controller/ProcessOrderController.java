package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessOrderController {

    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);

    public List<RiderBean> discoverAvailableRiders(OrderBean orderBean) {
        Order order = new Order(
                orderBean.getIdOrder(),
                orderBean.getAddress(),
                orderBean.getConsumer(),
                orderBean.getTelNumber(),
                orderBean.getTime(),
                orderBean.getStatus()
        );

        RiderDAO riderDAO = DAOFactory.getDAOFactory().createRiderDAO();

        try {
            List<Rider> riders = riderDAO.getAvailableRiders(order, order.getTime());
            return riders.stream()
                    .map(rs -> new RiderBean(rs.getIdRider(), rs.getName(), rs.isPermitZTL()))
                    .toList();
        } catch(Exception e) {
            logger.log(Level.WARNING, "Error while fetching available riders", e);
            return new ArrayList<>();
        }
    }

    public List<OrderBean> discoverPendingOrders(){

        logger.info("In discoverPendingOrders");

        List<Order> orders = new ArrayList<>();
        List<OrderBean> ordersBean = new ArrayList<>();

        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();

        try {
            orders = orderDAO.getPendingOrders();
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
            Printer.errorPrint(e.getMessage());
        }

        for (Order rs: orders){
            var orderBean = new OrderBean(rs.getIdOrder(),
                                          rs.getAddress(),
                                          rs.getCostumer(),
                                          rs.getTelNumber(),
                                          rs.getTime(),
                                          rs.getStatus());
            ordersBean.add(orderBean);
        }
        return ordersBean;
    }

    public void assignRider(OrderBean orderBean, RiderBean riderBean ) throws DAOException, BusinessException {
        Rider rider = new Rider(
                riderBean.getIdRider(),
                riderBean.getName(),
                riderBean.getPermitZTL()
        );
        Rider riderOrder = null;
        if(orderBean.getRider() != null) {
            RiderBean riderOrderBean = orderBean.getRider();
            riderOrder = new Rider(
                    riderOrderBean.getIdRider(),
                    riderOrderBean.getName(),
                    riderOrderBean.getPermitZTL()
            );
        }
        Order order = new Order(
                orderBean.getIdOrder(),
                orderBean.getAddress(),
                orderBean.getConsumer(),
                orderBean.getTelNumber(),
                riderOrder,
                orderBean.getTime(),
                orderBean.getStatus()
        );

        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();

        //interrogo il dao per sapere se l'ordine e' stato gia' assegnato
        Order orderDao = orderDAO.getOrderById(order.getIdOrder());
        order.checkIfNotOutdated(orderDao);
        orderDAO.updateOrder(order, rider);
    }
}