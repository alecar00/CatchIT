package com.alessandro.caracciolo.catchit.utils;

import com.alessandro.caracciolo.catchit.bean.OrderBean;
import com.alessandro.caracciolo.catchit.bean.RiderBean;

import java.util.Scanner;

public class Printer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_ORANGE = "\u001B[38;2;255;101;0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String SEPARATOR = "------------------------------------";

    private Printer() {}

    public static void print(String message){
        System.out.print(message);
    }

    public static void println(String message){
        System.out.println(message);
    }

    public static void printlnBlu(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    public static void printlnOrange(String message) {
        System.out.println(ANSI_ORANGE + message + ANSI_RESET);
    }

    //stampa messaggio di errore
    public static void errorPrint(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    public static void successPrint(String message) {System.out.println(ANSI_GREEN + message + ANSI_RESET);}

    //stampa errore scelta utente
    public static void invalidChoicePrint() {System.out.println(ANSI_RED + "Invalid choice. Try again..." + ANSI_RESET);}

    public static void printTitle(String title) {
        printlnBlu(SEPARATOR);
        printlnOrange(">> " + title.toUpperCase() + " <<");
        printlnBlu(SEPARATOR);
    }

    public static void printOrder(OrderBean orderBean, int nOrder) {
        printlnOrange("-" + nOrder + ") Order ID: #" + orderBean.getIdOrder());
        printlnOrange("   Consumer: " + orderBean.getConsumer());
        printlnOrange("   Address: " + orderBean.getAddress());
        printlnOrange("   Time: " + orderBean.getTime());
        printlnBlu(SEPARATOR);
    }

    public static void printRider(RiderBean riderBean, int index) {
        printlnOrange("-" + index + ") Rider ID: " + riderBean.getIdRider());
        printlnOrange("   Name: " + riderBean.getName());
        printlnOrange("   Permit ZTL: " + (riderBean.getPermitZTL() ? "Yes" : "No"));
        printlnBlu(SEPARATOR);
    }

    public static void waitForEnter() {
        Scanner scanner = new Scanner(System.in);
        printlnOrange("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static void clearConsole() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}