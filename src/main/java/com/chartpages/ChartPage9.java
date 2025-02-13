package com.chartpages;

import com.db.DatabaseServices;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Map;

public class ChartPage9 implements ChartPage {

    @Override
    public VBox getChartPage(Stage primaryStage, StackPane mainMenu) {
        // Récupérer les données pour le graphique (nombre d'offres par niveau d'études et télétravail)
        Map<String, Map<String, Integer>> data = DatabaseServices.getJobsByStudyLevelAndRemoteWork();

        // Créer l'axe des X (niveau d'études)
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Niveau d'études");

        // Créer l'axe des Y (nombre d'offres)
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre d'offres");

        // Créer le graphique croisé (BarChart)
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Offres d'emploi par niveau d'études et télétravail");

        // Réduire l'espacement entre les barres dans un groupe (les barres doivent être collées)
        barChart.setBarGap(0);  // Pas d'espace entre les barres du même groupe

        // Ajouter un peu d'espace entre les groupes de barres
        barChart.setCategoryGap(10);  // Ajouter un espace entre les groupes (par exemple entre les niveaux d'études)

        // Parcourir les données pour créer les séries
        for (Map.Entry<String, Map<String, Integer>> studyLevelEntry : data.entrySet()) {
            String studyLevel = studyLevelEntry.getKey();
            Map<String, Integer> remoteWorkData = studyLevelEntry.getValue();

            // Créer une série pour chaque niveau d'études
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(studyLevel); // Nom de la série (niveau d'études)

            // Ajouter les données pour télétravail dans chaque niveau d'études
            for (Map.Entry<String, Integer> remoteWorkEntry : remoteWorkData.entrySet()) {
                String remoteWork = remoteWorkEntry.getKey(); // "Yes" ou "No"
                int totalJobs = remoteWorkEntry.getValue();
                series.getData().add(new XYChart.Data<>(remoteWork, totalJobs));
            }

            // Ajouter la série au graphique
            barChart.getData().add(series);
        }

        // Créer un layout avec le graphique
        StackPane chartLayout = new StackPane(barChart);

        // Créer un VBox pour contenir uniquement le graphique
        VBox vbox = new VBox(10, chartLayout);
        return vbox;
    }
}
