/***
 * @author Whxismou
 * 
 *Clase encargada de la conexion y desconexion a la base de datos
 * 
 */

package atmmanagerlogic;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BBDDConexion {

    // private static final String URL_JDBC = System.getenv("URL_JDBC");
    // private static final String USER_JDBC = System.getenv("USER_JDBC");
    // private static final String PASSWORD_JDBC = System.getenv("PASSWORD_JDBC");

    private EnconderSHA enconderSHA;
    private Connection connection;
    private ConfigReader configReader;
    private String URL_JDBC;
    private String USER_JDBC;
    private String PASSWORD_JDBC;

    BBDDConexion() {
        enconderSHA = new EnconderSHA();
        configReader = new ConfigReader();
        URL_JDBC = configReader.getDbUrl();
        USER_JDBC = configReader.getDbUser();
        PASSWORD_JDBC = configReader.getDbPassword();
    }

    /***
     * Metodo encargado de abrir la conexion con la BBDD
     */
    protected void openConection() {
        try {
            connection = DriverManager.getConnection(URL_JDBC, USER_JDBC, PASSWORD_JDBC);
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * Metodo encargado de comprobar si el username introducido ya existe
     * 
     * @param actualUser Usuario que esta intentando acceder/registrar
     * @return [True(Si existe) | False(Si no existe)]
     * @throws NoSuchAlgorithmException
     */
    protected boolean isUserInDB(User actualUser) throws NoSuchAlgorithmException {
        boolean userInDB = false;
        PreparedStatement userCheckStatement = null;
        ResultSet userCheckResult = null;
        try {
            userCheckStatement = connection
                    .prepareStatement("SELECT * FROM users WHERE username = ?");
            userCheckStatement.setString(1, enconderSHA.encode(actualUser.getUsername()));
            userCheckResult = userCheckStatement.executeQuery();

            if (userCheckResult.next()) {
                userInDB = true;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            try {
                if (userCheckStatement != null) {
                    userCheckStatement.close();
                }
                if (userCheckResult != null) {
                    userCheckResult.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userInDB;

    }

    protected void registerUser(User userRegistered) throws NoSuchAlgorithmException {
        PreparedStatement registerStatement = null;
        try {
            registerStatement = connection.prepareStatement(
                    "INSERT INTO users (fullName, dni, email, username, password) VALUES (?, ?, ?, ?, ?)");
            registerStatement.setString(1, enconderSHA.encode(userRegistered.getFullName()));
            registerStatement.setString(2, enconderSHA.encode(userRegistered.getDni()));
            registerStatement.setString(3, enconderSHA.encode(userRegistered.getEmail()));
            registerStatement.setString(4, enconderSHA.encode(userRegistered.getUsername()));
            registerStatement.setString(5, enconderSHA.encode(userRegistered.getPassword()));

            int rowsAffected = registerStatement.executeUpdate();

            if (rowsAffected != 1) {
                System.out.println("ERROR: The user could not be registered");
            } else {
                System.out.println("User registered successfully");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (registerStatement != null) {
                    registerStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected int getBalance(User actualUser) throws NoSuchAlgorithmException {
        int balance = 0;

        PreparedStatement balanceStatement = null;

        try {
            balanceStatement = connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
            balanceStatement.setString(1, enconderSHA.encode(actualUser.getUsername()));

            ResultSet balanceResultSet = balanceStatement.executeQuery();

            if (balanceResultSet.next()) {
                balance = balanceResultSet.getInt("balance");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

    public void withdraw(User actualUser, int newBalance) throws NoSuchAlgorithmException {

        PreparedStatement withdrawStatement = null;

        try {
            withdrawStatement = connection.prepareStatement("UPDATE users SET balance = ? WHERE username = ?");
            withdrawStatement.setInt(1, newBalance);
            withdrawStatement.setString(2, enconderSHA.encode(actualUser.getUsername()));

            int rowsAffected = withdrawStatement.executeUpdate();

            if (rowsAffected != 1) {
                System.out.println("ERROR: The balance could not be updated");
            } else {
                System.out.println("Balance updated successfully");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deposit(User actualUser, int newBalance) throws NoSuchAlgorithmException {
        PreparedStatement deposiStatement = null;

        try {
            deposiStatement = connection.prepareStatement("UPDATE users SET balance = ? WHERE username = ?");
            deposiStatement.setInt(1, newBalance);
            deposiStatement.setString(2, enconderSHA.encode(actualUser.getUsername()));

            int rowsAffected = deposiStatement.executeUpdate();

            if (rowsAffected != 1) {
                System.out.println("ERROR: The balance could not be updated");
            } else {
                System.out.println("Balance updated successfully");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean isCorrectPassword2User(User actualUser) throws NoSuchAlgorithmException {
        PreparedStatement passwordStatement = null;
        boolean isCorrect = false;
        try {
            passwordStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            passwordStatement.setString(1, enconderSHA.encode(actualUser.getUsername()));
            passwordStatement.setString(2, enconderSHA.encode(actualUser.getPassword()));
            ResultSet rs = passwordStatement.executeQuery();
            if (rs.next()) {
                isCorrect = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isCorrect;

    }

    protected void addTransaction(User actualUser, int newBalance, char type) throws NoSuchAlgorithmException {
        PreparedStatement addTransactionStatement = null;

        try {
            addTransactionStatement = connection
                    .prepareStatement("INSERT INTO transactions (user_id, transaction_type, amount) VALUES (?, ?, ?)");

            addTransactionStatement.setInt(1, getIdUser(actualUser));

            if (type == 'D') {
                addTransactionStatement.setString(2, "Deposit");
            } else if (type == 'W') {
                addTransactionStatement.setString(2, "Withdraw");
            }

            addTransactionStatement.setInt(3, newBalance);

            int rowsAffected = addTransactionStatement.executeUpdate();

            if (rowsAffected != 1) {
                System.out.println("ERROR: The transaction could not be added");
            } else {
                System.out.println("Transaction added successfully");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getIdUser(User actualUser) throws NoSuchAlgorithmException {
        int idUser = 0;
        PreparedStatement getIdStatement = null;
        try {
            getIdStatement = connection.prepareStatement("SELECT user_id FROM users WHERE username = ?");
            getIdStatement.setString(1, enconderSHA.encode(actualUser.getUsername()));
            ResultSet rs = getIdStatement.executeQuery();
            if (rs.next()) {
                idUser = rs.getInt("user_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idUser;
    }

    protected void checkTransactions(User actualUser) throws NoSuchAlgorithmException {
        PreparedStatement checkTransactionsStatement = null;

        try {
            checkTransactionsStatement = connection.prepareStatement(
                    "SELECT transaction_type, amount FROM transactions WHERE user_id = ?");
            checkTransactionsStatement.setInt(1, getIdUser(actualUser));
            ResultSet rs = checkTransactionsStatement.executeQuery();
            while (rs.next()) {
                System.out.println("Transaction type: " + rs.getString("transaction_type") + " Amount: "
                        + rs.getInt("amount") + "€");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
