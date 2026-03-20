package Interface;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class Parcels {
    public VBox getParcelPane() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        
        Label header = new Label("PARCEL MANAGEMENT");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TableView<String[]> table = new TableView<>();
        TableColumn<String[], String> col1 = new TableColumn<>("Tracking #");
        TableColumn<String[], String> col2 = new TableColumn<>("Sender");
        TableColumn<String[], String> col3 = new TableColumn<>("Receiver");
        
        table.getColumns().addAll(col1, col2, col3);
        content.getChildren().addAll(header, new Button("+ Add New Parcel"), table);
        return content;
    }
}