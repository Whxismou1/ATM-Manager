/***
 * @author Whxismou
 * 
 *Clase encargada de la conexion y desconexion a la base de datos
 * 
 */

package atmmanagerlogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BBDDConexion {
    private static final String URL_JDBC = "jdbc:mysql://localhost:3306/atmmanager";
    private static final String USER_JDBC = "root2";
    private static final String PASSWORD_JDBC = "root2";
    private Connection connection;

    /***
     * Metodo encargado de abrir la conexion con la BBDD
     */
    protected void openConection() {
        try {
            connection = DriverManager.getConnection(URL_JDBC, USER_JDBC, PASSWORD_JDBC);
            System.out.println("Conexion establecida");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /***
     * Metodo encargado de cerrar la conexion con la BBDD
     */
    protected void closeConection() {
        try {
            connection.close();
            System.out.println("Conexion cerrada");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * Metodo encargado de comprobar si el username introducido ya existe
     * 
     * @param actualUser Usuario que esta intentando acceder/registrar
     * @return [True(Si existe) | False(Si no existe)]
     */
    protected boolean isUserInDB(User actualUser) {
        boolean userInDB = false;
        try {
            PreparedStatement userCheckStatement = connection
                    .prepareStatement("SELECT * FROM users WHERE username = ?");
            userCheckStatement.setString(1, actualUser.getUsername());
            ResultSet userCheckResult = userCheckStatement.executeQuery();

            if (userCheckResult.next()) {
                userInDB = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInDB;

    }

}
