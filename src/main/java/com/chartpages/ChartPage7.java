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

public class ChartPage7 implements ChartPage {

    @Override
    public VBox getChartPage(Stage primaryStage, StackPane mainMenu) {
        // Récupérer les données pour le graphique (nombre d'offres par expérience et type de contrat)
        Map<String, Map<String, Integer>> data = DatabaseServices.getJobsByExperienceAndContractType();

        // Créer l'axe des X (expérience requise)
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Expérience requise");

        // Créer l'axe des Y (nombre d'offres)
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre d'offres");

        // Créer le graphique croisé (BarChart)
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Offres d'emploi par expérience requise et type de contrat");

        // Réduire l'espacement entre les barres dans un groupe (les barres doivent être collées)
        barChart.setBarGap(0);  // Pas d'espace entre les barres du même groupe

        // Ajouter un peu d'espace entre les groupes de barres
        barChart.setCategoryGap(10);  // Ajouter un espace entre les groupes (par exemple entre l'expérience CDI et intérim)

        // Parcourir les données pour créer les séries
        for (Map.Entry<String, Map<String, Integer>> entry : data.entrySet()) {
            String experience = entry.getKey();
            Map<String, Integer> contractTypes = entry.getValue();

            // Créer une série pour chaque type de contrat
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(experience); // Nom de la série (expérience requise)

            // Ajouter les données pour chaque type de contrat
            for (Map.Entry<String, Integer> contractEntry : contractTypes.entrySet()) {
                String contractType = contractEntry.getKey();
                int totalJobs = contractEntry.getValue();
                series.getData().add(new XYChart.Data<>(contractType, totalJobs));
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
