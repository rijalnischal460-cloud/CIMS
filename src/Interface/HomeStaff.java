package Interface;

import controller.HomeStaffControl;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HomeStaff extends Application {
    private BorderPane mainLayout;
    private Label activeViewLabel;
    private HomeStaffControl controller;

    @Override
    public void start(Stage primaryStage) {
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #f4f4f4;");

        // 1. Header
        mainLayout.setTop(createHeader());

        // 2. Initialize Controller
        controller = new HomeStaffControl(mainLayout, activeViewLabel);

        // 3. Sidebar
        setupSidebar(primaryStage);
        
        // 4. FIX: Set Default View to "Dashboard" instead of "Parcels"
        controller.handleViewSwitch("Dashboard");

        primaryStage.setScene(new Scene(mainLayout, 1300, 800));
        primaryStage.setTitle("CIMS | Staff Operations");
        primaryStage.show();
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(15, 30, 15, 30));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        activeViewLabel = new Label("Staff Operations");
        activeViewLabel.setFont(Font.font("System", FontWeight.BOLD, 22));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label staffBadge = new Label("WAREHOUSE STAFF");
        staffBadge.setStyle("-fx-background-color: #e8f5e9; -fx-text-fill: #2e7d32; -fx-padding: 5 15; -fx-background-radius: 20; -fx-font-weight: bold;");

        header.getChildren().addAll(activeViewLabel, spacer, staffBadge);
        return header;
    }

    private void setupSidebar(Stage stage) {
        VBox sidebar = new VBox(5);
        sidebar.setPadding(new Insets(20, 10, 20, 10));
        sidebar.setPrefWidth(250);
        sidebar.setStyle("-fx-background-color: #2c3e50;");

        // Added "Dashboard" to the menu items
        String[] menuItems = {"Dashboard", "Parcels", "Inventory", "Dispatch"};
        
        for (String item : menuItems) {
            Button btn = createNavButton(item);
            btn.setOnAction(e -> controller.handleViewSwitch(item));
            sidebar.getChildren().add(btn);
        }

        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);
        
        Button logout = new Button("LOGOUT");
        logout.setMaxWidth(Double.MAX_VALUE);
        logout.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        
        sidebar.getChildren().addAll(bottomSpacer, logout);
        mainLayout.setLeft(sidebar);
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 20, 12, 20));
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        
        // Add hover effect
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        
        return btn;
    }

    public static void main(String[] args) { launch(args); }
}