package com.chartpages;

import com.db.DatabaseServices;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class ChartPage3 implements ChartPage {

    @Override

    public VBox getChartPage(Stage primaryStage, StackPane mainMenu) {  // Utilisation de StackPane ici

        // Récupérer les données pour le graphique (nombre d'offres par expérience requise)
        Map<String, Integer> data = DatabaseServices.getJobsByExperience();

        // Créer un BarChart
        BarChart<String, Number> barChart = new BarChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        barChart.setTitle("Nombre d'offres par Expérience Requise");

        // Créer une série de données pour le graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Offres par Expérience");

        // Vérifier que les données ne sont pas nulles et les ajouter au graphique
        if (data != null && !data.isEmpty()) {
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
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


        // Créer un VBox pour contenir uniquement le graphique (sans le bouton)
        VBox vbox = new VBox(10, chartLayout);

        return vbox;
    }
}
