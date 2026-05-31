package com.alessandro.caracciolo.catchit.utils;

public class Printer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_ORANGE = "\u001B[38;2;255;101;0m";

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

    //stampa errore scelta utente
    public static final void invalidChoicePrint() {System.out.println(ANSI_RED + "Invalid choice. Try again..." + ANSI_RESET);}
}