package com.alessandro.caracciolo.catchit.view.cli;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.controller.DeliveryController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.List;
import java.util.Scanner;


public class RiderHomePageViewCLI {
    private static final String SEPARATOR = "------------------------------------";
    private final DeliveryController deliveryController;
    private final String riderId;
    private final Scanner scanner;

    public RiderHomePageViewCLI(String riderId) {
        this.riderId = riderId;
        this.deliveryController = new DeliveryController();
        this.scanner = new Scanner(System.in);
    }

    public void initialize() {
        boolean running = true;
        while (running) {
            clearConsole();
            Printer.printTitle("RIDER DASHBOARD");

            showOrders();

            Printer.printlnOrange("\nOptions:");
            Printer.printlnOrange("1) Start Delivery");
            Printer.printlnOrange("0) Logout");
            Printer.printlnBlu(SEPARATOR);
            Printer.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleStartDelivery();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    Printer.invalidChoicePrint();
                    waitForEnter(scanner);
            }
        }
    }

    private void showOrders() {
        try {
            List<OrderBean> orders = deliveryController.showOrdersRider(this.riderId);
            if (orders == null || orders.isEmpty()) {
                Printer.printlnOrange("No orders currently assigned to you.\n");
            } else {
                Printer.printlnOrange(">> ASSIGNED ORDERS <<");
                int nOrder = 1;
                for (OrderBean orderBean : orders) {
                    Printer.printOrder(orderBean, nOrder);
                    nOrder++;
                }
            }
        } catch (DAOException e) {
            Printer.errorPrint("Error loading orders: " + e.getMessage());
        }
    }

    private void handleStartDelivery() {
        Printer.print("\nEnter Order ID to start delivery: ");
        String orderId = scanner.nextLine().trim();

        try {
            deliveryController.startDelivery(orderId);
            Printer.printlnBlu("\n✔️ Success! Order #" + orderId + " is now in delivery.");
        } catch (DAOException e) {
            Printer.errorPrint("\n❌ Database error: " + e.getMessage());
        } catch (BusinessException e) {
            Printer.errorPrint("\n⚠ Business error: " + e.getMessage());
        }
        waitForEnter(scanner);
    }

    private void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private void waitForEnter(Scanner scanner) {
        Printer.printlnOrange("\nPress Enter to continue...");
        scanner.nextLine();
    }
}