package com.alessandro.caracciolo.catchit.view.cli;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.controller.ProcessOrderController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.alessandro.caracciolo.catchit.utils.Printer.printlnBlu;
import static com.alessandro.caracciolo.catchit.utils.Printer.printlnOrange;

public class RestaurantViewCLI {
    private static final String SEPARATOR = "------------------------------------";
    private static final Logger logger = Logger.getLogger(RestaurantViewCLI.class.getName());

    public void initialize() {
        ProcessOrderController processOrderController = new ProcessOrderController();
        Scanner input = new Scanner(System.in);

        while(true) {
            List<OrderBean> orderBeans = processOrderController.discoverPendingOrders();

            if (orderBeans.isEmpty()) {
                clearConsole();
                printTitle("No Pending Orders");

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

            updateOrdersList(orderBeans);
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
                Printer.errorPrint("\n❌ Unable to assign order:" + e.getMessage());
                logger.severe("Error in assignRider:" + e.getMessage());
                waitForEnter(input);
            }
        }
    }

    private void updateRidersList(List<RiderBean> riderBeans) {
        clearConsole();
        printTitle("Available Riders\t0 - Back");
        int nRider = 1;
        for (RiderBean rider : riderBeans) {
            printRider(rider, nRider);
            nRider++;
        }
    }

    private void updateOrdersList(List<OrderBean> orderBeans) {
        clearConsole();
        printTitle("Pending Orders\t0 - Logout");
        int nOrder = 1;
        for (OrderBean orderBean : orderBeans) {
            printOrder(orderBean, nOrder);
            nOrder++;
        }
    }

    private void printOrder(OrderBean orderBean, int nOrder) {
        printlnOrange("-" + nOrder + ") Order ID: #" + orderBean.getIdOrder());
        printlnOrange("   Consumer: " + orderBean.getConsumer());
        printlnOrange("   Address: " + orderBean.getAddress());
        printlnOrange("   Time: " + orderBean.getTime());
        printlnBlu(SEPARATOR);
    }

    private void printRider(RiderBean riderBean, int nRider) {
        printlnOrange("-" + nRider + ") Rider ID: " + riderBean.getIdRider());
        printlnOrange("   Name: " + riderBean.getName());
        printlnOrange("   Permit ZTL: " + (riderBean.getPermitZTL() ? "Yes" : "No"));
        printlnBlu(SEPARATOR);
    }

    private void printTitle(String title) {
        printlnBlu(SEPARATOR);
        printlnOrange(">> " + title.toUpperCase() + " <<");
        printlnBlu(SEPARATOR);
    }

    private void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    /**
     * Legge un intero in modo sicuro, evitando che il programma crashi se l'utente digita una lettera.
     */
    private int readIntSafely(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Mette in pausa l'esecuzione finché l'utente non preme Invio.
     * Indispensabile per far leggere i messaggi di errore o successo prima del clearConsole().
     */
    private void waitForEnter(Scanner scanner) {
        Printer.printlnOrange("\nPress Enter to continue...");
        scanner.nextLine();
    }
}