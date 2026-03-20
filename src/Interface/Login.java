package Interface;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.DbConnection;
import java.sql.*;

public class Login extends Application {
    private boolean isRegisterMode = false;

    @Override
    public void start(Stage primaryStage) {
        HBox root = new HBox();
        root.setStyle("-fx-background-color: #ffffff;");

        // --- Left Brand Panel ---
        VBox brandPanel = new VBox(15);
        brandPanel.setAlignment(Pos.CENTER);
        brandPanel.setPrefWidth(400);
        brandPanel.setStyle("-fx-background-color: #1a1a1a;");

        Label brandLogo = new Label("CIMS");
        brandLogo.setFont(Font.font("System", FontWeight.EXTRA_BOLD, 60));
        brandLogo.setTextFill(Color.WHITE);
        
        Label tagLine = new Label("COURIER & INVENTORY");
        tagLine.setTextFill(Color.web("#888888"));
        tagLine.setStyle("-fx-letter-spacing: 2px;");
        
        brandPanel.getChildren().addAll(brandLogo, tagLine);

        // --- Right Form Panel ---
        VBox formPanel = new VBox();
        formPanel.setAlignment(Pos.CENTER);
        HBox.setHgrow(formPanel, Priority.ALWAYS);

        VBox formContainer = new VBox(15);
        formContainer.setMaxWidth(350);
        formContainer.setAlignment(Pos.CENTER_LEFT);

        Label welcomeLabel = new Label("Secure Access");
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        welcomeLabel.setPadding(new javafx.geometry.Insets(0, 0, 10, 0));

        TextField userField = createStyledField("Username");
        PasswordField passField = createStyledPassField("Password");
        PasswordField confirmField = createStyledPassField("Confirm Password");
        
        // Hidden by default for Register mode
        confirmField.setVisible(false);
        confirmField.setManaged(false);

        // Buttons
        Button adminBtn = new Button("SIGN IN AS ADMIN");
        Button staffBtn = new Button("SIGN IN AS WAREHOUSE STAFF");
        Button registerBtn = new Button("CREATE ACCOUNT");
        
        // Style Buttons
        String primaryStyle = "-fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold; -fx-background-radius: 5;";
        String secondaryStyle = "-fx-background-color: #4a4a4a; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold; -fx-background-radius: 5;";
        
        adminBtn.setStyle(primaryStyle);
        staffBtn.setStyle(secondaryStyle);
        registerBtn.setStyle(primaryStyle);
        
        adminBtn.setMaxWidth(Double.MAX_VALUE);
        staffBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        
        // Hide register button initially
        registerBtn.setVisible(false);
        registerBtn.setManaged(false);

        Hyperlink toggleLink = new Hyperlink("Don't have an account? Register");
        toggleLink.setStyle("-fx-text-fill: #555555; -fx-underline: false;");

        // --- Logic Handlers ---
        
        adminBtn.setOnAction(e -> handleLogin(userField.getText(), passField.getText(), "Admin", primaryStage));
        staffBtn.setOnAction(e -> handleLogin(userField.getText(), passField.getText(), "Staff", primaryStage));
        
        registerBtn.setOnAction(e -> {
            handleRegistration(userField.getText(), passField.getText(), confirmField.getText(), toggleLink);
        });

        toggleLink.setOnAction(e -> {
            isRegisterMode = !isRegisterMode;
            welcomeLabel.setText(isRegisterMode ? "Register User" : "Secure Access");
            
            // Toggle visibility for registration
            confirmField.setVisible(isRegisterMode);
            confirmField.setManaged(isRegisterMode);
            registerBtn.setVisible(isRegisterMode);
            registerBtn.setManaged(isRegisterMode);
            
            // Toggle visibility for login buttons
            adminBtn.setVisible(!isRegisterMode);
            adminBtn.setManaged(!isRegisterMode);
            staffBtn.setVisible(!isRegisterMode);
            staffBtn.setManaged(!isRegisterMode);
            
            toggleLink.setText(isRegisterMode ? "Already have an account? Sign In" : "Don't have an account? Register");
        });

        formContainer.getChildren().addAll(welcomeLabel, userField, passField, confirmField, adminBtn, staffBtn, registerBtn, toggleLink);
        formPanel.getChildren().add(formContainer);
        root.getChildren().addAll(brandPanel, formPanel);

        primaryStage.setTitle("CIMS - Login");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }

    private void handleLogin(String user, String pass, String role, Stage stage) {
        if (user.isEmpty() || pass.isEmpty()) {
            showDialog("Error", "Please fill in all fields.");
            return;
        }

        // Note: In a real app, you should use BCrypt for password hashing!
        String query = "SELECT * FROM users WHERE username=? AND password=? AND role=?";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setString(3, role);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                showDialog("Success", "Welcome, " + user + " (" + role + ")");
                // Redirect to Home and pass the role if necessary
                new HomeAdmin().start(new Stage());
                stage.close();
            } else {
                showDialog("Login Failed", "Invalid credentials for " + role + " role.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialog("Database Error", "Connection failed.");
        }
    }

    private void handleRegistration(String user, String pass, String conf, Hyperlink toggle) {
        if (user.isEmpty() || pass.isEmpty()) {
            showDialog("Error", "Fields cannot be empty.");
            return;
        }
        if (!pass.equals(conf)) {
            showDialog("Error", "Passwords do not match!");
            return;
        }

        // Defaulting new registrations to 'Staff' for security
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username, password, role) VALUES (?,?,?)")) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setString(3, "Staff"); 
            ps.executeUpdate();
            
            showDialog("Success", "Account created as Staff! Please sign in.");
            toggle.fire(); // Switch back to login mode
        } catch (SQLException e) {
            showDialog("Error", "Username already exists.");
        }
    }

    private TextField createStyledField(String p) {
        TextField t = new TextField();
        t.setPromptText(p);
        t.setPrefHeight(45);
        t.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dddddd; -fx-border-radius: 5; -fx-background-radius: 5;");
        return t;
    }

    private PasswordField createStyledPassField(String p) {
        PasswordField t = new PasswordField();
        t.setPromptText(p);
        t.setPrefHeight(45);
        t.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dddddd; -fx-border-radius: 5; -fx-background-radius: 5;");
        return t;
    }

    private void showDialog(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}