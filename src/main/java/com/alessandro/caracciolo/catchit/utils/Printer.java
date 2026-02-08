package com.alessandro.caracciolo.catchit.utils;

public class Printer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";


    private Printer() {}

    //stampa
    public static void print(String message){
        System.out.print(message);
    }

    //stampa e va a capo
    public static void println(String message){
        System.out.println(message);
    }

    //stampa la guida per ogni pagina in CLI
    public static void printlnBlu(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    //stampa messaggio di errore
    public static void errorPrint(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    //stampa errore scelta utente
    public static final void invalidChoicePrint() {System.out.println(ANSI_RED + "Invalid choice. Try again..." + ANSI_RED);}
}