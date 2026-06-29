package com.alessandro.caracciolo.catchit.view.cli;

import com.alessandro.caracciolo.catchit.controller.DeliveryController;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.Scanner;

import static com.alessandro.caracciolo.catchit.utils.Printer.*;

public class DeliveryingViewCLI {
    //to be implemented
    private final DeliveryController deliveryController;

    public DeliveryingViewCLI() {
        this.deliveryController = new DeliveryController();
    }

    public void initialize() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            clearConsole();
            printTitle("RIDER DASHBOARD");
            Printer.printlnOrange("1) Mark an order as Delivered");
            Printer.printlnOrange("0) Logout (Return to Login)");
            Printer.printlnBlu(SEPARATOR);
            Printer.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            if ("0".equals(choice)) {
                break;
            } else if ("1".equals(choice)) {
                handleCompleteOrder(scanner);
            } else {
                Printer.invalidChoicePrint();
                waitForEnter();
            }
        }
    }

    private void handleCompleteOrder(Scanner scanner) {
        Printer.print("\nEnter the ID of the order you just delivered: ");
        String orderId = scanner.nextLine().trim();

        try {
            deliveryController.setOrderCompleted(orderId);
            Printer.printlnBlu("\n✔️ Great job! Order #" + orderId + " marked as COMPLETED.");
        } catch (DAOException e) {
            Printer.errorPrint("\n❌ Error while completing the order: " + e.getMessage());
        }

        waitForEnter();
    }

}