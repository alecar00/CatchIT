package com.alessandro.caracciolo.catchit.view.cli;

import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.controller.LoginController;
import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.utils.Printer;

import java.util.Scanner;

import static com.alessandro.caracciolo.catchit.utils.Printer.SEPARATOR;
import static com.alessandro.caracciolo.catchit.utils.Printer.printTitle;

public class LoginViewCLI {
    private final LoginController loginController;

    public LoginViewCLI() {
        this.loginController = new LoginController();
    }

    public void initialize() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Printer.clearConsole();
            printTitle("WELCOME IN CATCHIT!");
            Printer.printlnOrange("Type 'exit' as username to quit.\nType 'register' as username to register.");
            Printer.printlnBlu(SEPARATOR);

            Printer.print("Username: ");
            String username = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(username)) {
                Printer.printlnOrange("\nExiting system. Goodbye!");
                System.exit(0);
            } else if ("register".equalsIgnoreCase(username)) {
                RegistrationViewCLI registrationViewCLI = new RegistrationViewCLI();
                registrationViewCLI.initialize();
                continue;
            }

            Printer.print("Password: ");
            String password = scanner.nextLine().trim();

            UserBean userBean = new UserBean(username, password);


            try {
                Printer.printlnOrange("\nVerifying credentials...");
                int role = loginController.verifyCredentials(userBean);

                switch(role){
                    case 1:
                        Printer.printlnBlu("\n✔️ Successfully logged in as RESTAURANT!");
                        Printer.waitForEnter();

                        RestaurantViewCLI restaurantViewCLI = new RestaurantViewCLI();
                        restaurantViewCLI.initialize();
                        break;

                    case 2:
                        Printer.printlnBlu("\n✔️ Successfully logged in as RIDER!");
                        Printer.waitForEnter();

                        RiderHomePageViewCLI riderHomePageViewCLI = new RiderHomePageViewCLI(userBean.getUsername());
                        riderHomePageViewCLI.initialize();
                        break;

                    default:
                        Printer.errorPrint("\n❌ Unknown role. Please contact the administrator.");
                        Printer.waitForEnter();
                }

            } catch (BusinessException e) {
                Printer.errorPrint("\n❌ Invalid credentials: " + e.getMessage());
                Printer.waitForEnter();
            } catch (DAOException e) {
                Printer.errorPrint("\n❌ System Error: " + e.getMessage());
                Printer.waitForEnter();
            }
        }
    }
}