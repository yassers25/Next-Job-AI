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

public class ChartPage8 implements ChartPage {

    @Override
    public VBox getChartPage(Stage primaryStage, StackPane mainMenu) {
        // Récupérer les données pour le graphique (nombre d'offres par ville et expérience requise)
        Map<String, Map<String, Integer>> data = DatabaseServices.getJobsByCityAndExperience();

        // Créer l'axe des X (ville)
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Ville");

        // Créer l'axe des Y (nombre d'offres)
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre d'offres");

        // Créer le graphique croisé (BarChart)
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Offres d'emploi par ville et expérience requise");

        // Réduire l'espacement entre les barres dans un groupe (les barres doivent être collées)
        barChart.setBarGap(0);  // Pas d'espace entre les barres du même groupe

        // Ajouter un peu d'espace entre les groupes de barres
        barChart.setCategoryGap(10);  // Ajouter un espace entre les groupes (par exemple entre les villes)

        // Parcourir les données pour créer les séries
        for (Map.Entry<String, Map<String, Integer>> cityEntry : data.entrySet()) {
            String city = cityEntry.getKey();
            Map<String, Integer> experienceData = cityEntry.getValue();

            // Créer une série pour chaque ville
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(city); // Nom de la série (ville)

            // Ajouter les données pour chaque niveau d'expérience dans la ville
            for (Map.Entry<String, Integer> experienceEntry : experienceData.entrySet()) {
                String experience = experienceEntry.getKey();
                int totalJobs = experienceEntry.getValue();
                series.getData().add(new XYChart.Data<>(experience, totalJobs));
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
