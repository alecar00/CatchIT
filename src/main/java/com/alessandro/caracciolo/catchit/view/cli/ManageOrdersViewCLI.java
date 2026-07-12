package com.alessandro.caracciolo.catchit.view.cli;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.controller.ManageOrdersController;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.alessandro.caracciolo.catchit.utils.Printer.clearConsole;

public class ManageOrdersViewCLI {

    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);
    private final ManageOrdersController manageOrdersController = new ManageOrdersController();
    private final Scanner input = new Scanner(System.in);

    public void initialize() {
        while (true) {
            List<OrderBean> orders = manageOrdersController.discoverOrders();

            if (orders.isEmpty()) {
                clearConsole();
                Printer.printTitle("No Orders");
                logger.info("No Orders found");
                Printer.printlnOrange("Press Enter to refresh the list, or type '0' to Logout.");
            } else {
                updateOrdersList(orders);
                Printer.print("Press Enter to refresh the list, type '1' to process orders or '0' to Logout.");
            }
            String choice = input.nextLine().trim();
            logger.info(() -> "User input: " + (choice.isEmpty() ? "ENTER (Refresh)" : choice));

            if ("0".equals(choice)) {
                return;

            } else if ("1".equals(choice) && !orders.isEmpty()) {
                ProcessOrderViewCLI processOrderViewCLI = new ProcessOrderViewCLI();
                processOrderViewCLI.initialize();

            }

        }
    }

    private void updateOrdersList(List<OrderBean> orderBeans) {
        clearConsole();
        Printer.printTitle("Orders");
        int nOrder = 1;
        for (OrderBean orderBean : orderBeans) {
            Printer.printCompleteOrder(orderBean, nOrder);
            nOrder++;
        }
    }

}
