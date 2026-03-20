package Interface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HomeAdmin extends Application {
    private BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) {
        mainLayout = new BorderPane();

        // Simple Sidebar
        VBox sidebar = new VBox(10);
        sidebar.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15;");
        sidebar.setPrefWidth(200);

        Button btnViewReports = new Button("System Reports");
        btnViewReports.setMaxWidth(Double.MAX_VALUE);

        // --- ACTION: RUN THE REPORT PAGE ---
        btnViewReports.setOnAction(e -> {
            Report reportModule = new Report();
            // This is how you "run" it: set the center to the ScrollPane
            mainLayout.setCenter(reportModule.getReportPane());
        });

        sidebar.getChildren().add(btnViewReports);
        mainLayout.setLeft(sidebar);

        Scene scene = new Scene(mainLayout, 1000, 650);
        primaryStage.setTitle("CIMS - Admin Terminal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) { launch(args); }
}