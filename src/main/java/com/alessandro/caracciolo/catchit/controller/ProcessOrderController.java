package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.dao.RiderDAO;
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

    public List<Rider> discoverAvailableRiders(OrderBean order){
        List<Rider> rider = new ArrayList<>();
        List<RiderBean> riderBean = new ArrayList<>();

        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        RiderDAO riderDAO = DAOFactory.getDAOFactory().createRiderDAO();

        return null;
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
            logger.info("Founded" + orderBean.toString());

        }

        if (ordersBean == null) {
            logger.severe("ERRORE CRITICO: La lista ordersBean è NULL!");
        } else {
            logger.info("Ho ricevuto dal DB: " + ordersBean.size() + " ordini.");
        }
        return ordersBean;
    }

}