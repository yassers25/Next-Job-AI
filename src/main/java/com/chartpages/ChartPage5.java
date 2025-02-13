package com.chartpages;


import com.db.DatabaseServices;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class ChartPage5 implements ChartPage {

    @Override

    public VBox getChartPage(Stage primaryStage, StackPane mainMenu) {

        // Récupérer les données pour le graphique (nombre d'offres par type de contrat)
        Map<String, Integer> data = DatabaseServices.getJobsByContractType();

        // Créer un graphique circulaire (PieChart)
        PieChart pieChart = new PieChart();


        if (data != null && !data.isEmpty()) {
            // Calculer le total des valeurs
            int total = 0;
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                total += entry.getValue();
            }

            // Ajouter les données au graphique en incluant les pourcentages
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                double percentage = (entry.getValue() / (double) total) * 100;
                String label = entry.getKey() + " (" + String.format("%.2f", percentage) + "%)";
                PieChart.Data slice = new PieChart.Data(label, entry.getValue());
                pieChart.getData().add(slice);
            }
        } else {
            System.out.println("Aucune donnée trouvée pour le graphique par type de contrat.");

        }

        // Créer un layout avec le graphique
        StackPane chartLayout = new StackPane(pieChart);


        // Créer un VBox pour contenir seulement le graphique (sans le bouton)
        VBox vbox = new VBox(10, chartLayout);

        return vbox;
    }
}
