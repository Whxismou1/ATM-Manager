package atmmanagerlogic;

import java.util.Scanner;

public class MainMenu {

    private Scanner sc;

    public MainMenu() {
        sc = new Scanner(System.in);
    }

    protected void start() {
        int option;
        do {
            System.out.println("Bienvenido al ATM Manager");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");

            option = getOption();

            switch (option) {
                case 1:
                    System.out.println("Iniciando sesion");
                    break;
                case 2:
                    System.out.println("Registrando usuario");
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
            System.out.print("Try again: ");
        }
        return sc.nextInt();

    }
}
