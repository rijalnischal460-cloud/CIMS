package controller;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DispatchControl {
    
    public Node getLayout() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30));
        grid.setHgap(15);
        grid.setVgap(15);

        Label header = new Label("New Dispatch Assignment");
        header.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        grid.add(header, 0, 0, 2, 1);

        grid.add(new Label("Assign Driver:"), 0, 1);
        ComboBox<String> drivers = new ComboBox<>();
        drivers.getItems().addAll("Ramesh Thapa", "Suresh Gurung", "Maya Devi");
        grid.add(drivers, 1, 1);

        grid.add(new Label("Route:"), 0, 2);
        TextField route = new TextField();
        route.setPromptText("e.g. Kathmandu to Butwal");
        grid.add(route, 1, 2);

        Button confirm = new Button("Confirm Dispatch");
        confirm.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-padding: 10 20;");
        grid.add(confirm, 1, 3);

        return grid;
    }
}