package controller;
import Interface.HomeAdmin;
import model.DbConnection; // Ensure this import is correct
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.sql.*;

public class LoginControl {

    public void processLogin(String user, String pass, Stage loginStage) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        // FIXED: Changed DbConnection to DatabaseConnection
        try (Connection conn = DbConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Login Success - Switch to Home
                new HomeAdmin().start(new Stage());
                loginStage.close();
            } else {
                showAlert("Access Denied", "Invalid database credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not connect to the server: " + e.getMessage());
        }
    }

    public boolean processRegistration(String user, String pass, String confirmPass) {
        if (user.isEmpty() || pass.isEmpty() || !pass.equals(confirmPass)) {
            showAlert("Error", "Validation failed.");
            return false;
        }

        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, user);
            pstmt.setString(2, pass); 
            pstmt.executeUpdate();
            
            showAlert("Success", "Account created in database!");
            return true;
        } catch (SQLException e) {
            // Error code 1062 is "Duplicate Entry" in MySQL
            if (e.getErrorCode() == 1062) {
                showAlert("Error", "Username already exists.");
            } else {
                e.printStackTrace();
                showAlert("Database Error", e.getMessage());
            }
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}