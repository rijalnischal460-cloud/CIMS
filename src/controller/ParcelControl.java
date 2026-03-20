package controller;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ParcelControl {
    
    public Node getLayout() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: #ffffff;");

        // Header Section
        HBox header = new HBox(15);
        Label title = new Label("Parcel Registry");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        TextField search = new TextField();
        search.setPromptText("Search Tracking ID...");
        search.setPrefWidth(250);
        search.setStyle("-fx-background-radius: 20; -fx-padding: 8 15;");

        header.getChildren().addAll(title, spacer, search);

        // Simple Table Placeholder 
        TableView<String> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No active parcels found in registry."));
        
        container.getChildren().addAll(header, table);
        return container;
    }
}