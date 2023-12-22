package atmmanagerlogic;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class LoginRegister {
    private BBDDConexion bbddConexion;
    private OperationsMenu operationsMenu;

    LoginRegister() {
        bbddConexion = new BBDDConexion();
        operationsMenu = new OperationsMenu();
    }

    protected void logIn(Scanner sc) {
        System.out.println("\n***** Login MENU *****");

        System.out.print("Introduce the username: ");
        String userIntroduced = sc.next();

        System.out.print("Introduce the password: ");
        String passwordIntroduced = new String(System.console().readPassword());

        User actualUser = new User(userIntroduced, passwordIntroduced);

        bbddConexion.openConection();
        try {
            if (!bbddConexion.isUserInDB(actualUser)) {
                System.out.println("ERROR: The username or password is incorrect");
                bbddConexion.closeConection();
                return;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            if (!bbddConexion.isCorrectPassword2User(actualUser)) {
                System.out.println("ERROR: The username or password is incorrect");
                bbddConexion.closeConection();
                return;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        bbddConexion.closeConection();
        operationsMenu.start(sc, actualUser);

    }

    protected void register(Scanner sc) {
        System.out.println("\n***** SingUp MENU *****");

        System.out.print("Introduce your fullName: ");
        String fullName = sc.next();

        System.out.print("Introduce your DNI: ");
        String dni = sc.next();

        System.out.print("Introduce your email: ");
        String email = sc.next();

        System.out.print("Introduce your username: ");
        String username = sc.next();

        bbddConexion.openConection();
        try {
            if (bbddConexion.isUserInDB(new User(username, ""))) {
                System.out.println("ERROR: The username already exists");
                return;
            }
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        bbddConexion.closeConection();

        System.out.print("Introduce your password: ");
        String password = new String(System.console().readPassword());

        System.out.print("\nRepeat your password: ");
        String passwordRepeat = new String(System.console().readPassword());

        if (!password.equals(passwordRepeat)) {
            System.out.println("ERROR: The passwords are not the same");
            return;
        }

        User userRegistered = new User(fullName, dni, email, username, password);
        bbddConexion.openConection();

        try {
            bbddConexion.registerUser(userRegistered);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        bbddConexion.closeConection();

    }

}
