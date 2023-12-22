package atmmanagerlogic;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class OperationsMenu {

    private BBDDConexion bbddConexion;

    OperationsMenu() {
        bbddConexion = new BBDDConexion();
    }

    protected void start(Scanner sc, User actualUser) {
        System.out.println("Operations Menu");
        int option;

        do {
            printOperationsMenu();
            option = getOption(sc);

            switch (option) {
                case 1:
                    withdraw(sc, actualUser);
                    break;
                case 2:
                    deposit(sc, actualUser);
                    break;
                case 3:
                    checktransactions(sc, actualUser);
                case 4:
                    System.out.println("Exiting ATM Manager");
                    System.exit(0);
                default:
                    System.out.println("ERROR: Invalid option [1-4]");
                    break;
            }

        } while (option != 4);
    }

    private void checktransactions(Scanner sc, User actualUser) {
        System.out.println("Check Transactions Menu");
        bbddConexion.openConection();
        try {
            bbddConexion.checkTransactions(actualUser);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        bbddConexion.closeConection();
    }

    private void withdraw(Scanner sc, User actualUser) {
        int actualBalance = 0;
        System.out.println("Withdraw Menu");
        bbddConexion.openConection();
        try {
            actualBalance = bbddConexion.getBalance(actualUser);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        bbddConexion.closeConection();
        System.out.println("Your actual balance is: " + actualBalance);

        System.out.print("Introduce the amount to withdraw: ");
        int amount = getOption(sc);

        if (amount <= 0) {
            System.out.println("ERROR: The amount must be greater than 0");
            return;
        }

        if (amount > actualBalance) {
            System.out.println("ERROR: You don't have enough money");
            return;
        }

        int newBalance = actualBalance - amount;
        bbddConexion.openConection();
        try {
            bbddConexion.withdraw(actualUser, newBalance);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            bbddConexion.addTransaction(actualUser, newBalance, 'W');
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        bbddConexion.closeConection();
    }

    private void deposit(Scanner sc, User actualUser) {
        int actualBalance = 0;
        System.out.println("Deposit Menu");
        bbddConexion.openConection();
        try {
            actualBalance = bbddConexion.getBalance(actualUser);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        bbddConexion.closeConection();

        System.out.print("Introduce the amount to deposit: ");
        int amount = getOption(sc);

        if (amount <= 0) {
            System.out.println("ERROR: The amount must be greater than 0");
            return;
        }

        int newBalance = actualBalance + amount;

        bbddConexion.openConection();
        try {
            bbddConexion.deposit(actualUser, newBalance);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            bbddConexion.addTransaction(actualUser, newBalance, 'D');
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        bbddConexion.closeConection();
    }

    private void printOperationsMenu() {
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Check Movementsu");
        System.out.println("4. Exit");
    }

    private int getOption(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next(); // Limpiar el búfer de entrada
            System.out.println("ERROR: The option must be a number.");
            System.out.print("Try again [1-4]: ");
        }
        return sc.nextInt();
    }

}
