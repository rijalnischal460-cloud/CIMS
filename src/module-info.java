module Courier_Management_System {
    // Core JavaFX modules for UI and Layouts
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    // Database connectivity module (CRITICAL for model.DbConnection)
    requires java.sql;

    // Allow JavaFX to access your Interface package for starting the Application
    opens Interface to javafx.graphics, javafx.fxml;
    
    // If your DbConnection is in a package named 'model', open it too
    opens model to java.sql;

    // Export your packages so other modules/the JVM can see them
    exports Interface;
    exports model; 
}