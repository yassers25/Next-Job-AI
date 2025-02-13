package com.chartpages;


import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public interface ChartPage {
    VBox getChartPage(Stage primaryStage, StackPane mainMenu);  // Utiliser StackPane au lieu de VBox
}
