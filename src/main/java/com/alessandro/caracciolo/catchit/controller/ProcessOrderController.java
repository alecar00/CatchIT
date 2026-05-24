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

    public List<RiderBean> discoverAvailableRiders(OrderBean orderBean){
        List<Rider> riders = new ArrayList<>();
        List<RiderBean> ridersBean = new ArrayList<>();

        //convertire il bean in entity da passare al dao. Da valutare un mapper
        Order order = new Order(
                orderBean.getIdOrder(),
                orderBean.getAddress(),
                orderBean.getConsumer(),
                orderBean.getTelNumber(),
                orderBean.getTime(),
                orderBean.getStatus()
        );

        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();
        RiderDAO riderDAO = DAOFactory.getDAOFactory().createRiderDAO();

        // logica di prelievo riders disponibili
        try {
            riders = riderDAO.getAvailableRiders(order, order.getTime());
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
        }

        //convertire riders entity dal dao in riders bean da restituire alla view
        for (Rider rs : riders) {
            var riderBean = new RiderBean(
                    rs.getIdRider(),
                    rs.getName(),
                    rs.isPermitZTL()
            );
            ridersBean.add(riderBean);
        }

        return ridersBean;
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