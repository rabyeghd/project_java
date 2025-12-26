package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility using Singleton Pattern
 * Manages MySQL connection via JDBC
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    // Database configuration
    private static final String URL = "jdbc:mysql://localhost:3306/centre_formation";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Private constructor for Singleton pattern
    private DatabaseConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DRIVER);
            // Establish connection
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✓ Database connection established successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("✗ Failed to connect to database!");
            e.printStackTrace();
        }
    }

    /**
     * Get the singleton instance of DatabaseConnection
     * 
     * @return DatabaseConnection instance
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    /**
     * Get the active database connection
     * 
     * @return Connection object
     */
    public Connection getConnection() {
        try {
            // Check if connection is closed or null, recreate if necessary
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("✗ Error checking/recreating connection!");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Close the database connection
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✓ Database connection closed successfully!");
            } catch (SQLException e) {
                System.err.println("✗ Error closing database connection!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Test the database connection
     * 
     * @return true if connection is valid, false otherwise
     */
    public boolean testConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
