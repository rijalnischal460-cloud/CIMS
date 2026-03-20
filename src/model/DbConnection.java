package model; // Make sure this matches the folder name

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/courier_db"; 
    private static final String USER = "root"; // Your MySQL username
    private static final String PASS = "nischal10"; // Your MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Database connection created successfully!");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("❌ MySQL Driver not found! Did you add the JAR?", e);
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect to database!");
            throw e;
        }
    }

    // Quick test main method
    public static void main(String[] args) {
        try {
            Connection conn = DbConnection.getConnection();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}