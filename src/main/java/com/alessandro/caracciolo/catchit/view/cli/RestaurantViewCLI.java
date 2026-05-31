package com.alessandro.caracciolo.catchit.view.cli;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.controller.ProcessOrderController;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;

import java.util.List;
import java.util.Scanner;

import static com.alessandro.caracciolo.catchit.utils.Printer.printlnBlu;
import static com.alessandro.caracciolo.catchit.utils.Printer.printlnOrange;

public class RestaurantViewCLI {
    private ProcessOrderController processOrderController;
    private static final String SEPARATOR = "------------------------------------";

    public void initialize() {
        this.processOrderController = new ProcessOrderController();
        Scanner input = new Scanner(System.in);
        while(true) {
            List<OrderBean> orderBeans = processOrderController.discoverPendingOrders();
            updateOrdersList(orderBeans);
            int orderChoice = input.nextInt();

            OrderBean selectedOrder;
            if (orderChoice == 0) break;
            if (orderChoice > 0 && orderChoice <= orderBeans.size()) {
                selectedOrder = orderBeans.get(orderChoice - 1);
            } else continue;

            List<RiderBean> riderBeans = processOrderController.discoverAvailableRiders(selectedOrder);
            updateRidersList(riderBeans);
            int riderChoice = input.nextInt();

            RiderBean selectedRider;
            if(riderChoice == 0) continue;
            if (riderChoice >= 0 && riderChoice <= riderBeans.size()) {
                selectedRider = riderBeans.get(riderChoice - 1);
            }else continue;

            try {
                processOrderController.assignRider(selectedOrder, selectedRider);
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateRidersList(List<RiderBean> riderBeans) {
        clearConsole();
        printTitle("Available Riders\t0 - back");
        int nRider = 1;
        for (RiderBean rider : riderBeans) {
            printRider(rider, nRider);
            nRider ++;
        }
    }

    private void updateOrdersList(List<OrderBean> orderBeans) {
        clearConsole();
        printTitle("Pending Orders\t0 - exit");
        int nOrder = 1;
        for (OrderBean orderBean : orderBeans) {
            printOrder(orderBean, nOrder);
            nOrder++;
        }
    }

    private void printOrder(OrderBean orderBean, int nOrder) {
        printlnOrange("-" + nOrder + ") Order ID: " + orderBean.getIdOrder());
        printlnOrange("-- Consumer: " + orderBean.getConsumer());
        printlnOrange("-- Address: " + orderBean.getAddress());
        printlnOrange("-- Time: " + orderBean.getTime());
        printlnBlu(SEPARATOR);
    }

    private void printRider(RiderBean riderBean, int nRider) {
        printlnOrange("-" + nRider + ") Rider ID: " + riderBean.getIdRider());
        printlnOrange("-- Name: " + riderBean.getName());
        printlnBlu(SEPARATOR);
    }

    private void printTitle(String title) {
        printlnBlu(SEPARATOR);
        printlnOrange("-- " + title);
        printlnBlu(SEPARATOR);
    }

    private void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
