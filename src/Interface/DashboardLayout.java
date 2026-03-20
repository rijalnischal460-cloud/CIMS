package Interface;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class DashboardLayout extends BorderPane {

    public VBox sidebar;
    public HBox header;

    public DashboardLayout(String sectionTitle) {
        // Sidebar
        sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #1a1a1a;");
        sidebar.setPrefWidth(200);

        String[] items = {"Home", "Parcels", "Inventory", "Dispatch", "Reports", "Logout"};
        for (String item : items) {
            Button b = new Button(item);
            b.setMaxWidth(Double.MAX_VALUE);
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px;");
            sidebar.getChildren().add(b);
        }

        // Header
        header = new HBox();
        header.setPadding(new Insets(15));
        Label title = new Label(sectionTitle);
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        header.getChildren().add(title);

        setLeft(sidebar);
        setTop(header);
    }

    public VBox createCard(String title, String value) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 15; -fx-border-radius: 5;");
        Label t = new Label(title);
        t.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label v = new Label(value);
        v.setStyle("-fx-font-size: 18px; -fx-text-fill: #1a1a1a;");
        card.getChildren().addAll(t, v);
        return card;
    }
}
