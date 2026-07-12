package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageOrdersController {
    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);


    public List<OrderBean> discoverOrders(){

        logger.info("In discoverOrders");

        List<Order> orders = new ArrayList<>();
        List<OrderBean> ordersBean = new ArrayList<>();

        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();

        try {
            orders = orderDAO.getOrders();
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
        logger.info(() -> "Found " + ordersBean.size() + " orders");
        return ordersBean;
    }
}
