package Interface;

import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Dispatch {
    public VBox getDispatchPane() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));

        TableView<String[]> table = new TableView<>();
        TableColumn<String[], String> idCol = new TableColumn<>("ID");
        TableColumn<String[], String> statusCol = new TableColumn<>("Status");

        table.getColumns().addAll(idCol, statusCol);
        content.getChildren().addAll(new Label("DISPATCH QUEUE"), table);
        return content;
    }
}