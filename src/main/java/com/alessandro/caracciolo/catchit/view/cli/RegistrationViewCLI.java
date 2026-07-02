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
    RegisterController registerController = new RegisterController();
    Scanner scanner = new Scanner(System.in);

    public void initialize() {

        boolean isDone = false;

        while (!isDone) {
            printTitle("REGISTRATION");

            printlnOrange("Enter role: ");
            printlnBlu("1 - Rider");
            printlnBlu("2 - Restaurant");
            String selectedRole = scanner.nextLine().trim();

            if (!selectedRole.equals("1") && !selectedRole.equals("2")) {
                invalidChoicePrint();
            } else {
                printlnOrange("Enter username: ");
                String username = scanner.nextLine().trim();

                printlnOrange("Enter password: ");
                String password = scanner.nextLine().trim();

                switch (selectedRole) {
                    case "1":
                        processRider(username, password);
                        break; // Questo break esce solo dallo switch, non dal while

                    case "2":
                        processRestaurant(username, password);
                        break;

                    default:
                        invalidChoicePrint();
                }

                waitForEnter();
                isDone = true;
            }
        }
    }
    private void processRider(String username,String password){
        String role = "RIDER";
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
    }

    private void processRestaurant (String username, String password){
        String role = "RESTAURANT";
        UserBean userBeanRestaurant = new UserBean(username, password, role);

        try {
            registerController.startRestaurantRegistration(userBeanRestaurant);
            successPrint("User: " + userBeanRestaurant.getUsername() + " registered!");
        } catch (InvalidRegistrationException | UsernameAlreadyUsed | DAOException e) {
            errorPrint(e.getMessage());
        }
    }
}