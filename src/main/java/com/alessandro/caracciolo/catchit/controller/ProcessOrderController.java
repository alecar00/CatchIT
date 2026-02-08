package com.alessandro.caracciolo.catchit.controller;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.ArrayList;
import java.util.List;

public class ProcessOrderController {

    public List<Rider> discoverAvailableRiders(OrderBean order){
        List<Rider> avBeans = new ArrayList<>();
        return null;
    }

    public List<OrderBean> discoverPendingOrders(){
        List<Order> orders = new ArrayList<>();
        List<OrderBean> ordersBean = new ArrayList<>();

        OrderDAO orderDAO = DAOFactory.getDAOFactory().createOrderDAO();

        try {
            orders = orderDAO.getPendingOrders();
        }catch(Exception e){
            Printer.errorPrint(e.getMessage());
        }

        for (Order rs: orders){
            var riderBean = new RiderBean(rs.getRider().getIdRider());
            var orderBean = new OrderBean(rs.getIdOrder(),
                                          rs.getAddress(),
                                          rs.getCostumer(),
                                          rs.getTelNumber(),
                                          riderBean,
                                          rs.getTime(),
                                          rs.getStatus());
            ordersBean.add(orderBean);
        }
        return ordersBean;
    }

}