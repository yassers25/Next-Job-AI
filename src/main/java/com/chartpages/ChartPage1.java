package com.chartpages;

import com.db.DatabaseServices;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Map;

public class ChartPage1 implements ChartPage {

    @Override

    public VBox getChartPage(Stage primaryStage, StackPane mainMenu) {  // Utilisation de StackPane

        // Récupérer les données pour le graphique (nombre d'offres par ville)
        Map<String, Integer> data = DatabaseServices.getJobsByCity();

        // Créer un graphique circulaire (PieChart)
        PieChart pieChart = new PieChart();

        
        // Calculer la somme totale pour les pourcentages
        int total = data.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            double percentage = (entry.getValue() / (double) total) * 100;
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            
            // Ajouter un label personnalisé avec le pourcentage
            slice.setName(String.format("%s %.1f%%", entry.getKey(), percentage));
            
            pieChart.getData().add(slice);

        }

        // Créer un layout avec le graphique
        StackPane chartLayout = new StackPane(pieChart);


        // Créer un VBox pour contenir uniquement le graphique (sans le bouton)
        VBox vbox = new VBox(10, chartLayout);  // Utilisation de VBox ici

        return vbox;
    }
}
