package bank.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton-style helper that provides a single shared JDBC Connection
 * to the MySQL database for the whole application.
 *
 * Update DB_URL / DB_USER / DB_PASSWORD to match your local MySQL setup,
 * and make sure the MySQL Connector/J jar is on your classpath.
 */
public final class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_management_system?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "spider";

    private static Connection connection;

    private DBConnection() {
        // utility class, no instances
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
