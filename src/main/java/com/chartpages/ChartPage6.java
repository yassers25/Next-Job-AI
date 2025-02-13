package com.chartpages;

import com.db.DatabaseServices;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class ChartPage6 implements ChartPage {

    @Override

    public VBox getChartPage(Stage primaryStage, StackPane mainMenu) {

        // Récupérer les données pour le graphique (nombre d'offres par télétravail)
        Map<String, Integer> data = DatabaseServices.getJobsByRemoteWork();

        // Créer un BarChart
        BarChart<String, Number> barChart = new BarChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        barChart.setTitle("Nombre d'offres par Télétravail");

        // Créer une série de données pour le graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Offres par Télétravail");

        // Vérifier que les données ne sont pas nulles et les ajouter au graphique
        if (data != null && !data.isEmpty()) {
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                // Vérifier que les valeurs ne sont pas nulles
                if (entry.getKey() != null && entry.getValue() != null) {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                } else {
                    // Log pour détecter une donnée invalide
                    System.out.println("Donnée invalide : clé ou valeur null : " + entry);
                }
            }

            // Ajouter la série de données au BarChart
            barChart.getData().add(series);
        } else {
            // Afficher un message d'erreur ou un graphique vide si aucune donnée n'est disponible
            System.out.println("Aucune donnée disponible pour afficher le graphique.");
        }

        // Créer un layout avec le graphique
        StackPane chartLayout = new StackPane(barChart);

        // Créer un VBox pour contenir seulement le graphique
        VBox vbox = new VBox(10, chartLayout);

        return vbox;
    }
}
