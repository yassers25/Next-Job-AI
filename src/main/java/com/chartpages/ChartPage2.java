package com.chartpages;

import java.util.Map;

import com.db.DatabaseServices;

import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChartPage2 implements ChartPage {

    @Override
    public VBox getChartPage(Stage primaryStage, StackPane mainMenu) {  // Utilisation de StackPane

        // Récupérer les données pour le graphique (nombre d'offres par secteur d'activité)
        Map<String, Integer> data = DatabaseServices.getJobsByActivitySector();

        // Créer un graphique circulaire (PieChart)
        PieChart pieChart = new PieChart();

        if (data != null && !data.isEmpty()) {
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
        } else {
            System.out.println("Aucune donnée trouvée pour le graphique par secteur d'activité.");
        }

        // Créer un layout avec le graphique
        StackPane chartLayout = new StackPane(pieChart);

        // Créer un VBox pour contenir uniquement le graphique (sans le bouton)
        VBox vbox = new VBox(10, chartLayout);

        // Retourner le VBox contenant le graphique
        return vbox;
    }
}
