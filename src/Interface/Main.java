package Interface;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Start with the Login screen
        new Login().start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
