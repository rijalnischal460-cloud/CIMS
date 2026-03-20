package Interface;

import controller.InventoryControl;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.property.SimpleStringProperty;

public class Inventory {
    private TableView<Item> table = new TableView<>();
    private InventoryControl control = new InventoryControl(); // Link the controller
    private ObservableList<Item> masterData;

    public VBox getInventoryPane() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label title = new Label("STOCK INVENTORY");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField search = new TextField();
        search.setPromptText("Filter inventory by name...");

        setupTable();
        
        // Use Controller to load data
        masterData = control.getAllInventory();
        
        // Setup Filtering
        FilteredList<Item> filteredData = new FilteredList<>(masterData, p -> true);
        search.textProperty().addListener((obs, old, val) -> {
            filteredData.setPredicate(item -> {
                if (val == null || val.isEmpty()) return true;
                return item.getName().toLowerCase().contains(val.toLowerCase());
            });
        });

        table.setItems(filteredData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        content.getChildren().addAll(title, search, table);
        return content;
    }

    private void setupTable() {
        TableColumn<Item, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(f -> f.getValue().nameProperty());
        
        TableColumn<Item, String> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(f -> f.getValue().stockProperty());

        table.getColumns().addAll(nameCol, stockCol);
    }

    // Move the Item class inside here or to a model package
    public static class Item {
        private final SimpleStringProperty name, stock;
        public Item(String n, String s) { 
            this.name = new SimpleStringProperty(n); 
            this.stock = new SimpleStringProperty(s); 
        }
        public String getName() { return name.get(); }
        public SimpleStringProperty nameProperty() { return name; }
        public SimpleStringProperty stockProperty() { return stock; }
    }
}