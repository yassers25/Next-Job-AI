package com.chartpages;

import com.db.DatabaseServices;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Map;

public class ChartPage11 implements ChartPage {

    @Override
    public VBox getChartPage(Stage primaryStage, StackPane mainMenu) {  // Utilisation de StackPane

        // Récupérer les données pour le graphique (par exemple, nombre d'offres par secteur d'activité)
        Map<String, Integer> data = DatabaseServices.getFunction();

        // Créer un graphique circulaire (PieChart)
        PieChart pieChart = new PieChart();

        // Calculer la somme totale pour les pourcentages
        int total = data.values().stream().mapToInt(Integer::intValue).sum();

        // Ajouter les tranches du graphique avec les pourcentages
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            double percentage = (entry.getValue() / (double) total) * 100;
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());

            // Ajouter un label personnalisé avec le pourcentage
            slice.setName(String.format("%s %.1f%%", entry.getKey(), percentage));

            pieChart.getData().add(slice);
        }

        // Créer un StackPane pour contenir le graphique
        StackPane chartLayout = new StackPane(pieChart);

        // Créer un VBox pour organiser le graphique et tout autre élément futur
        VBox vbox = new VBox(10, chartLayout);  // Utilisation de VBox ici pour la mise en page

        return vbox;
    }
}
