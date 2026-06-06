package com.alessandro.caracciolo.catchit.view.cli;

import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.controller.LoginController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.Scanner;

public class LoginViewCLI {
    private static final String SEPARATOR = "========================================";
    private final LoginController loginController;

    public LoginViewCLI() {
        this.loginController = new LoginController();
    }

    public void initialize() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            clearConsole();
            printTitle("WELCOME IN CATCHIT!");
            Printer.printlnOrange("Please login to continue (type 'exit' as username to quit).");
            Printer.printlnBlu(SEPARATOR);

            Printer.print("Username: ");
            String username = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(username)) {
                Printer.printlnOrange("\nExiting system. Goodbye!");
                System.exit(0);
            }

            Printer.print("Password: ");
            String password = scanner.nextLine().trim();

            UserBean userBean = new UserBean(username, password);


            try {
                Printer.printlnOrange("\nVerifying credentials...");
                int role = loginController.verifyCredentials(userBean);

                if (role == 1) {
                    Printer.printlnBlu("\n✔️ Successfully logged in as RESTAURANT!");
                    waitForEnter(scanner);

                    RestaurantViewCLI restaurantViewCLI = new RestaurantViewCLI();
                    restaurantViewCLI.initialize();

                } else if (role == 2) {
                    Printer.printlnBlu("\n✔️ Successfully logged in as RIDER!");
                    waitForEnter(scanner);

                    // TODO: avvia la DeliveryingViewCLI
                    Printer.printlnOrange("\nRider Dashboard CLI in development...");
                    // DeliveryingViewCLI deliveryView = new DeliveryingViewCLI();
                    // deliveryView.initialize();

                } else {
                    Printer.errorPrint("\n❌ Unknown role. Please contact the administrator.");
                    waitForEnter(scanner);
                }

            } catch (BusinessException e) {
                Printer.errorPrint("\n❌ Invalid credentials: " + e.getMessage());
                waitForEnter(scanner);
            } catch (DAOException e) {
                Printer.errorPrint("\\n❌ System Error: " + e.getMessage());
                waitForEnter(scanner);
            }
        }
    }

    private void printTitle(String title) {
        Printer.printlnBlu(SEPARATOR);
        Printer.printlnOrange(">> " + title + " <<");
        Printer.printlnBlu(SEPARATOR);
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