package atmmanagerlogic;

import java.util.Scanner;

public class MainMenu {

    private Scanner sc;
    private LoginRegister loginRegister;

    public MainMenu() {
        sc = new Scanner(System.in);
        loginRegister = new LoginRegister();
    }

    protected void start() {
        int option;
        do {
            printMainMenu();

            option = getOption();

            switch (option) {
                case 1:
                    loginRegister.logIn(sc);
                    break;
                case 2:
                    loginRegister.register(sc);
                    break;
                case 3:
                    System.out.println("Exiting ATM Manager");
                    System.exit(0);
                default:
                    System.out.println("ERROR: Invalid option [1-3]");
                    break;
            }

        } while (option != 3);

    }

    private int getOption() {
        while (!sc.hasNextInt()) {
            sc.next(); // Limpiar el búfer de entrada
            System.out.println("ERROR: The option must be a number.");
            System.out.print("Try again [1-3]: ");
        }
        return sc.nextInt();

    }

    private void printMainMenu() {
        System.out.println("Bienvenido al ATM Manager");
        System.out.println("1. Log In");
        System.out.println("2. Sign up");
        System.out.println("3. Exit");
    }

}
