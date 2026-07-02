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

public class RestaurantViewCLI {
    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);
    private final ProcessOrderController processOrderController = new ProcessOrderController();
    private final Scanner input = new Scanner(System.in);

    public void initialize() {
        while (true) {
            List<OrderBean> orders = processOrderController.discoverPendingOrders();

            if (orders.isEmpty()) {
                clearConsole();
                Printer.printTitle("No Pending Orders");
                Printer.printlnOrange("Press Enter to refresh the list, or type '0' to Logout.");

                // Se è 0, usciamo dal ciclo!
                if ("0".equals(input.nextLine().trim())) {
                    break;
                }
            } else {
                updateOrdersList(orders);
                Printer.print("\nChoose an order to assign (0 to Logout): ");
                int choice = readIntSafely(input);

                // Se la scelta è 0, usciamo dal ciclo e facciamo Logout
                if (choice == 0) {
                    break;
                }

                // Se la scelta è valida, passiamo l'ordine al metodo successivo
                if (choice > 0 && choice <= orders.size()) {
                    handleOrderSelection(orders.get(choice - 1));
                } else {
                    Printer.invalidChoicePrint();
                    waitForEnter();
                }
            }
        }
    }

    private void handleOrderSelection(OrderBean selectedOrder) {
        List<RiderBean> riders = processOrderController.discoverAvailableRiders(selectedOrder);

        if (riders.isEmpty()) {
            Printer.errorPrint("\n❌ Warning: No riders available!");
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
                Printer.printlnBlu("\n✔️ Success!");
                waitForEnter();
            } catch (DAOException | BusinessException e) {
                Printer.errorPrint("\n❌ Error: " + e.getMessage());
                waitForEnter();
            }
        } else {
            Printer.invalidChoicePrint();
            waitForEnter();
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
    private int readIntSafely(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException _) {
            return -1;
        }
    }
}