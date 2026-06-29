package com.alessandro.caracciolo.catchit.view.cli;

import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.bean.UserBean;
import com.alessandro.caracciolo.catchit.controller.RegisterController;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.exceptions.InvalidRegistrationException;
import com.alessandro.caracciolo.catchit.exceptions.UsernameAlreadyUsed;

import java.util.Scanner;

import static com.alessandro.caracciolo.catchit.utils.Printer.*;

public class RegistrationViewCLI {
    public void initialize() {
        RegisterController registerController = new RegisterController();
        Scanner scanner = new Scanner(System.in);

        boolean isDone = false;

        while (!isDone) {
            printTitle("REGISTRATION");

            printlnOrange("Enter role: ");
            printlnBlu("1 - Rider");
            printlnBlu("2 - Restaurant");
            String role = scanner.nextLine().trim();

            if (!role.equals("1") && !role.equals("2")) {
                invalidChoicePrint();
            } else {
                printlnOrange("Enter username: ");
                String username = scanner.nextLine().trim();

                printlnOrange("Enter password: ");
                String password = scanner.nextLine().trim();

                switch (role) {
                    case "1":
                        role = "RIDER";
                        UserBean userBeanRider = new UserBean(username, password, role);

                        printlnOrange("Enter name: ");
                        String name = scanner.nextLine().trim();
                        RiderBean riderBean = new RiderBean(username, name);

                        try {
                            registerController.startRiderRegistration(userBeanRider, riderBean);
                            successPrint("User: " + userBeanRider.getUsername() + " registered!\nRider: " + riderBean.getName() + " created!");
                        } catch (InvalidRegistrationException | UsernameAlreadyUsed | DAOException e) {
                            errorPrint(e.getMessage());
                        }
                        break; // Questo break esce solo dallo switch, non dal while

                    case "2":
                        role = "RESTAURANT";
                        UserBean userBeanRestaurant = new UserBean(username, password, role);

                        try {
                            registerController.startRestaurantRegistration(userBeanRestaurant);
                            successPrint("User: " + userBeanRestaurant.getUsername() + " registered!");
                        } catch (InvalidRegistrationException | UsernameAlreadyUsed | DAOException e) {
                            errorPrint(e.getMessage());
                        }
                        break;
                }

                waitForEnter();
                isDone = true;
            }
        }
    }
}