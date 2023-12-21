package atmmanagerlogic;

import java.util.Scanner;

public class LoginRegister {
    private BBDDConexion bbddConexion;

    LoginRegister() {
        bbddConexion = new BBDDConexion();
    }

    protected void logIn(Scanner sc) {
        System.out.println("Log In Menu");

        System.out.print("Username: ");
        String usernameIntroduced = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User actualUser = new User(usernameIntroduced, password);

        bbddConexion.openConection();
        if (!bbddConexion.isUserInDB(actualUser)) {
            System.out.println("ERROR: The username or password is incorrect");
            bbddConexion.closeConection();
        } else {
            bbddConexion.closeConection();
        }

    }

    protected void register(Scanner sc) {
        System.out.println("Sign Up Menu");
    }

}
