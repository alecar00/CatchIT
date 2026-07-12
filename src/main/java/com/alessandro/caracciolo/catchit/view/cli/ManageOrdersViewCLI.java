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
                Printer.printlnOrange("Press Enter to refresh the list, or type '0' to Logout.");

                // Se è 0, usciamo dal ciclo!
                if ("0".equals(input.nextLine().trim())) {
                    return;
                }
            } else {
                updateOrdersList(orders);
                Printer.print("Press Enter to refresh the list, type '1' to process orders or '0' to Logout.");
                int choice = readIntSafely(input);

                // Se la scelta è 0, usciamo dal ciclo e facciamo Logout
                if (choice == 0) {
                    return;
                } else if (choice == 1) {
                    ProcessOrderViewCLI processOrderViewCLI = new ProcessOrderViewCLI();
                    processOrderViewCLI.initialize();
                }
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
    private int readIntSafely(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException _) {
            return -1;
        }
    }

}
