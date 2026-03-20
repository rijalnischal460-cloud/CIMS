package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HomeStaffControl {
    private BorderPane mainLayout;
    private Label activeViewLabel;

    public HomeStaffControl(BorderPane mainLayout, Label activeViewLabel) {
        this.mainLayout = mainLayout;
        this.activeViewLabel = activeViewLabel;
    }

    public void handleViewSwitch(String view) {
        activeViewLabel.setText(view);
        
        switch (view) {
            case "Dashboard":
                mainLayout.setCenter(createDashboardView());
                break;
            case "Parcels":
                mainLayout.setCenter(new Label("Parcels Table Content Goes Here"));
                break;
            // Add other cases here...
            default:
                mainLayout.setCenter(new Label("View not found"));
        }
    }

    private VBox createDashboardView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(40));
        container.setAlignment(Pos.TOP_LEFT);

        Label welcome = new Label("Welcome back, Staff member!");
        welcome.setFont(Font.font("System", 24));
        
        Label instruction = new Label("Select a category from the sidebar to manage warehouse operations.");
        instruction.setStyle("-fx-text-fill: #7f8c8d;");

        container.getChildren().addAll(welcome, instruction);
        return container;
    }
}