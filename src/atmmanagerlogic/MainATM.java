package atmmanagerlogic;

public class MainATM {
    public static void main(String[] args) {
        // MainMenu MainMenu = new MainMenu();
        // MainMenu.start();
        BBDDConexion bbdd = new BBDDConexion();
        bbdd.openConection();
        bbdd.closeConection();

    }
}
