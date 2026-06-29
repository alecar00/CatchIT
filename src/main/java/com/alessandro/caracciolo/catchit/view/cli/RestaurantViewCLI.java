package com.alessandro.caracciolo.catchit.view.cli;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.controller.ProcessOrderController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class RestaurantViewCLI {
    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);

    public void initialize() {
        ProcessOrderController processOrderController = new ProcessOrderController();
        Scanner input = new Scanner(System.in);

        while(true) {
            List<OrderBean> orderBeans = processOrderController.discoverPendingOrders();

            if (orderBeans.isEmpty()) {
                clearConsole();
                Printer.printTitle("No Pending Orders");

                Printer.printlnOrange("Press Enter to refresh the list, or type '0' to Logout.");
                String choice = input.nextLine().trim();
                if ("0".equals(choice)) {
                    break;
                }
                continue;
            }

            updateOrdersList(orderBeans);
            Printer.print("\nChoose an order to assign (0 to Logout): ");

            int orderChoice = readIntSafely(input);

            if (orderChoice == 0) break;
            if (orderChoice < 0 || orderChoice > orderBeans.size()) {
                Printer.invalidChoicePrint();
                waitForEnter(input);
                continue;
            }

            OrderBean selectedOrder = orderBeans.get(orderChoice - 1);

            List<RiderBean> riderBeans = processOrderController.discoverAvailableRiders(selectedOrder);

            if (riderBeans.isEmpty()) {
                Printer.errorPrint("\n❌ Warning: No riders available for the requested time (" + selectedOrder.getTime() + ")!");
                waitForEnter(input);
                continue;
            }

            updateRidersList(riderBeans);
            Printer.print("\nChoose a rider to assign the order to (0 to go back): ");

            int riderChoice = readIntSafely(input);

            if(riderChoice == 0) continue;
            if (riderChoice < 0 || riderChoice > riderBeans.size()) {
                Printer.invalidChoicePrint();
                waitForEnter(input);
                continue;
            }

            RiderBean selectedRider = riderBeans.get(riderChoice - 1);

            try {
                processOrderController.assignRider(selectedOrder, selectedRider);
                Printer.printlnBlu("\n✔️ Order #" + selectedOrder.getIdOrder() + " successfully assigned to " + selectedRider.getName() + "!");
                waitForEnter(input);
            } catch (DAOException | BusinessException e) {
                Printer.errorPrint("\n❌ Unable to assign order: " + e.getMessage());
                logger.severe("Error in assignRider: " + e.getMessage());
                waitForEnter(input);
            }
        }
    }

    private void updateRidersList(List<RiderBean> riderBeans) {
        clearConsole();
        Printer.printTitle("Available Riders\t0 - Back");
        int nRider = 1;
        for (RiderBean rider : riderBeans) {
            Printer.printRider(rider, nRider);
            nRider++;
        }
    }

    private void updateOrdersList(List<OrderBean> orderBeans) {
        clearConsole();
        Printer.printTitle("Pending Orders\t0 - Logout");
        int nOrder = 1;
        for (OrderBean orderBean : orderBeans) {
            Printer.printOrder(orderBean, nOrder);
            nOrder++;
        }
    }

    private void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private int readIntSafely(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void waitForEnter(Scanner scanner) {
        Printer.printlnOrange("\nPress Enter to continue...");
        scanner.nextLine();
    }
}