package com.alessandro.caracciolo.catchit.view.cli;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.controller.ProcessOrderController;

import java.util.List;

public class RestaurantViewCLI {
    private ProcessOrderController processOrderController;

    public void initialize() {
        this.processOrderController = new ProcessOrderController();

        List<OrderBean> orderBeans = processOrderController.discoverPendingOrders();
        updateOrdersList(orderBeans);
    }

    private void updateOrdersList(List<OrderBean> orderBeans) {
        printTitle("Pending Orders");
        for (OrderBean orderBean : orderBeans) {
            printOrder(orderBean);
        }
    }

    private void printOrder(OrderBean orderBean) {
        System.out.println("- Order ID: " + orderBean.getIdOrder());
        System.out.println("-- Consumer: " + orderBean.getConsumer());
        System.out.println("-- Address: " + orderBean.getAddress());
        System.out.println("-- Time: " + orderBean.getTime());
    }

    private void printRider(RiderBean riderBean) {
        System.out.println("- Rider ID: " + riderBean.getIdRider());
        System.out.println("-- Name: " + riderBean.getName());
    }

    private void printTitle(String title) {
        System.out.println("------------------------------------");
        System.out.println("-- " + title);
        System.out.println("------------------------------------");
    }
}
