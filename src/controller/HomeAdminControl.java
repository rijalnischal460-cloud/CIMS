package controller;

import Interface.*; // Import your UI pane classes
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class HomeAdminControl {

    private final BorderPane mainLayout;
    private final Label activeViewLabel;

    public HomeAdminControl(BorderPane mainLayout, Label activeViewLabel) {
        this.mainLayout = mainLayout;
        this.activeViewLabel = activeViewLabel;
    }

    /**
     * Switches the center view and updates header text.
     * @param view The name of the view to load
     */
    public void handleViewSwitch(String view) {
        // 1. Update the header text
        activeViewLabel.setText("Admin Console > " + view);

        // 2. Logic to switch the center pane
        try {
            switch (view) {
                case "Parcels" -> mainLayout.setCenter(new Parcels().getParcelPane());
                case "Inventory" -> mainLayout.setCenter(new Inventory().getInventoryPane());
                case "Dispatch" -> mainLayout.setCenter(new Dispatch().getDispatchPane()); // Added this!
                case "Reports" -> mainLayout.setCenter(new Report().getReportPane());
                case "User Management" -> mainLayout.setCenter(createPlaceholder("User Management & Permissions"));
                case "Settings" -> mainLayout.setCenter(createPlaceholder("System Configuration"));
                case "Dashboard" -> mainLayout.setCenter(createPlaceholder("Admin Dashboard Statistics"));
                default -> mainLayout.setCenter(createPlaceholder("View Not Found: " + view));
            }
        } catch (Exception e) {
            // Logs the specific error to the console for debugging
            e.printStackTrace(); 
            mainLayout.setCenter(createPlaceholder("Error loading " + view + "\nCheck console for details."));
        }
    }

    /**
     * Optional: Updates the sidebar button styles to show which one is active.
     */
    public void updateActiveButtonStyle(VBox sidebar, String activeText) {
        for (Node node : sidebar.getChildren()) {
            if (node instanceof Button btn) {
                if (btn.getText().equals(activeText)) {
                    btn.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-alignment: CENTER_LEFT; -fx-padding: 12 20;");
                } else if (!btn.getText().equals("LOGOUT")) {
                    btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; -fx-alignment: CENTER_LEFT; -fx-padding: 12 20;");
                }
            }
        }
    }

    private StackPane createPlaceholder(String title) {
        Label label = new Label(title);
        label.setFont(Font.font(20));
        StackPane p = new StackPane(label);
        p.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 20;");
        return p;
    }
}