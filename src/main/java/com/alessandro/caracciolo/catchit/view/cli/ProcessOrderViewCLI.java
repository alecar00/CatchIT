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

import static com.alessandro.caracciolo.catchit.utils.Printer.clearConsole;
import static com.alessandro.caracciolo.catchit.utils.Printer.waitForEnter;

public class ProcessOrderViewCLI {
    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);
    private final ProcessOrderController processOrderController = new ProcessOrderController();
    private final Scanner input = new Scanner(System.in);

    public void initialize() {
        while (true) {
            List<OrderBean> orders = processOrderController.discoverPendingOrders();

            if (orders.isEmpty()) {
                clearConsole();
                Printer.printTitle("No Pending Orders");
                logger.info("No Pending Orders found.");
                Printer.printlnOrange("Press Enter to refresh the list, or type '0' to Logout.\n");
            } else {
                updateOrdersList(orders);
                Printer.print("\nChoose an order to assign (0 to go Back, or Press Enter to refresh): ");
            }

            String inputStr = input.nextLine().trim();
            logger.info(() -> "User input: " + (inputStr.isEmpty() ? "ENTER (Refresh)" : inputStr));

            if ("0".equals(inputStr)) {
                return;

            } else if (!inputStr.isEmpty() && !orders.isEmpty()) {
                try {
                    int choice = Integer.parseInt(inputStr);

                    if (choice > 0 && choice <= orders.size()) {
                        handleOrderSelection(orders.get(choice - 1));
                    } else {
                        Printer.invalidChoicePrint();
                        waitForEnter();
                    }
                } catch (NumberFormatException e) {
                    Printer.invalidChoicePrint();
                    waitForEnter();
                }
            }
        }
    }

    private void handleOrderSelection(OrderBean selectedOrder) {
        List<RiderBean> riders = processOrderController.discoverAvailableRiders(selectedOrder);

        if (riders.isEmpty()) {
            Printer.errorPrint("\nWarning: No riders available!");
            waitForEnter();
            return; // Interrompe questo metodo e torna al menu degli ordini
        }

        updateRidersList(riders);
        Printer.print("\nChoose a rider (0 to go back): ");
        int choice = readIntSafely(input);

        if (choice == 0) {
            return; // L'utente vuole tornare indietro. Il return lo riporta al while in initialize()
        }

        if (choice > 0 && choice <= riders.size()) {
            try {
                processOrderController.assignRider(selectedOrder, riders.get(choice - 1));
                Printer.printlnBlu("\nSuccess!");
                waitForEnter();
            } catch (DAOException | BusinessException e) {
                Printer.errorPrint("\nError: " + e.getMessage());
                waitForEnter();
            }
        } else {
            Printer.invalidChoicePrint();
            waitForEnter();
        }
    }

    private void updateRidersList(List<RiderBean> riderBeans) {
        clearConsole();
        Printer.printTitle("Available Riders\t0 - to go Back");
        int nRider = 1;
        for (RiderBean rider : riderBeans) {
            Printer.printRider(rider, nRider);
            nRider++;
        }
    }

    private void updateOrdersList(List<OrderBean> orderBeans) {
        clearConsole();
        Printer.printTitle("Pending Orders\t0 - to go Back");
        int nOrder = 1;
        for (OrderBean orderBean : orderBeans) {
            Printer.printOrder(orderBean, nOrder);
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