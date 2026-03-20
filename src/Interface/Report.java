package Interface;

import controller.ReportControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Map; // CRITICAL IMPORT

public class Report {
    private ReportControl control = new ReportControl();

    public ScrollPane getReportPane() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(25));
        content.setStyle("-fx-background-color: #ffffff;");

        // --- Header ---
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        VBox titleArea = new VBox(5);
        Label header = new Label("SYSTEM ANALYTICS & LOGS");
        header.setFont(Font.font("System", FontWeight.BOLD, 22));
        Label subHeader = new Label("Review system performance and delivery logs");
        subHeader.setTextFill(Color.GRAY);
        titleArea.getChildren().addAll(header, subHeader);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button refreshBtn = new Button("Refresh Data");
        refreshBtn.setStyle("-fx-background-color: #f1f2f6; -fx-text-fill: #2f3542; -fx-cursor: hand;");
        headerBox.getChildren().addAll(titleArea, spacer, refreshBtn);

        // --- Search Bar ---
        HBox toolBar = new HBox(15);
        toolBar.setAlignment(Pos.CENTER_LEFT);
        toolBar.setPadding(new Insets(10, 0, 10, 0));
        TextField searchField = new TextField();
        searchField.setPromptText("Filter reports...");
        searchField.setPrefSize(300, 35);
        toolBar.getChildren().addAll(new Label("Search:"), searchField);

        // --- Table View ---
        TableView<ReportData> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(400);

        TableColumn<ReportData, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<ReportData, String> titleCol = new TableColumn<>("Metric/Activity");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<ReportData, String> valCol = new TableColumn<>("Count/Status");
        valCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        table.getColumns().addAll(dateCol, titleCol, valCol);

        // --- Load Data from Controller ---
        try {
            Map<String, Integer> stats = control.getSummaryStats();
            if (stats != null) {
                stats.forEach((title, value) -> {
                    table.getItems().add(new ReportData("2026-03-19", title, String.valueOf(value)));
                });
            }
        } catch (Exception e) {
            System.err.println("Error loading stats: " + e.getMessage());
        }

        // --- Bottom Buttons ---
        HBox actions = new HBox(15);
        actions.setAlignment(Pos.CENTER_RIGHT);
        Button pdfBtn = new Button("EXPORT PDF");
        Button excelBtn = new Button("EXPORT EXCEL");
        String btnStyle = "-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-cursor: hand;";
        pdfBtn.setStyle(btnStyle);
        excelBtn.setStyle(btnStyle);
        actions.getChildren().addAll(pdfBtn, excelBtn);

        content.getChildren().addAll(headerBox, new Separator(), toolBar, table, actions);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent; -fx-background: #ffffff;");
        return scrollPane;
    }

    // Bean-compliant Helper Class
    public static class ReportData {
        private String date, title, value;
        public ReportData(String date, String title, String value) {
            this.date = date; this.title = title; this.value = value;
        }
        public String getDate() { return date; }
        public String getTitle() { return title; }
        public String getValue() { return value; }
    }
}