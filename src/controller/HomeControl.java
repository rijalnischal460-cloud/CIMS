package controller;

import Interface.Login;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Controller class to handle logic for the Home/Dashboard view.
 * Separates UI layout from application behavior.
 */
public class HomeControl {

    /**
     * Handles the logout process.
     * Closes the dashboard and opens the Login stage.
     */
    public void processLogout(Stage dashboardStage) {
        try {
            // Initialize and show the Login screen
            new Login().start(new Stage());
            // Close the current Dashboard window
            dashboardStage.close();
        } catch (Exception ex) {
            System.err.println("Error during logout: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Fetches the chart data. 
     * In a production environment, this would call a Database or API.
     */
    public XYChart.Series<String, Number> loadWeeklyStats() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Parcel Volume");

        // Mock data representing weekly performance
        series.getData().add(new XYChart.Data<>("Mon", 45));
        series.getData().add(new XYChart.Data<>("Tue", 80));
        series.getData().add(new XYChart.Data<>("Wed", 65));
        series.getData().add(new XYChart.Data<>("Thu", 90));
        series.getData().add(new XYChart.Data<>("Fri", 110));

        return series;
    }

    /**
     * Logic for tracking search.
     */
    public void searchTrackingId(String id) {
        if (id == null || id.isEmpty()) return;
        
        System.out.println("Querying database for Tracking ID: " + id);
        // Add logic here to filter a TableView or show a popup
    }
}