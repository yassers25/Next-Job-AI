    package com.ui.dashboard;

    import java.util.Map;

    import com.chartpages.ChartPage;
    import com.chartpages.ChartPage1;
    import com.chartpages.ChartPage10;
    import com.chartpages.ChartPage11;
    import com.chartpages.ChartPage2;
    import com.chartpages.ChartPage3;
    import com.chartpages.ChartPage4;
    import com.chartpages.ChartPage5;
    import com.chartpages.ChartPage6;
    import com.chartpages.ChartPage7;
    import com.chartpages.ChartPage8;
    import com.chartpages.ChartPage9;
    import com.db.DatabaseServices;

    import javafx.animation.ScaleTransition;
    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
    import javafx.scene.layout.BorderPane;
    import javafx.scene.layout.GridPane;
    import javafx.scene.layout.StackPane;
    import javafx.scene.layout.VBox;
    import javafx.scene.text.Font;
    import javafx.scene.text.FontWeight;
    import javafx.scene.text.Text;
    import javafx.stage.Stage;
    import javafx.util.Duration;

    public class OverviewPage extends ScrollPane {
        private Stage primaryStage;
        private BorderPane root;
        private Text title;

        public OverviewPage(Stage primaryStage, BorderPane root, Text title) {
            this.primaryStage = primaryStage;
            this.root = root;
            this.title = title;
            
            VBox content = new VBox();
            content.setPadding(new Insets(20));
            content.setSpacing(20);
            content.setStyle("-fx-background-color: #f8f9fa;");
            
            // Title
            Label overviewTitle = new Label("Overview");
            overviewTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            
            // Statistics Grid
            GridPane statsGrid = new GridPane();
            statsGrid.setHgap(20);
            statsGrid.setVgap(20);
            statsGrid.setPadding(new Insets(20));
            
            // Add statistics cards
            Map<String, String> response = DatabaseServices.getJobCountsBySite();
            statsGrid.add(createStatCard("Total Jobs", response.get("all")), 0, 0);
            statsGrid.add(createStatCard("Rekrute Jobs", response.get("rekrute")), 1, 0);
            statsGrid.add(createStatCard("Emploi Jobs", response.get("emploi.ma")), 2, 0);
            statsGrid.add(createStatCard("M-Jobs", response.get("m-job")), 3, 0);

            // Create chart navigation with all buttons
            VBox chartNavigation = createChartNavigation();
            
            content.getChildren().addAll(overviewTitle, statsGrid, chartNavigation);
            
            setContent(content);
            setFitToWidth(true);
            setStyle("-fx-background-color: #f8f9fa;");
        }


        

        private VBox createChartNavigation() {
            VBox chartMenu = new VBox(20);
            chartMenu.setAlignment(Pos.CENTER);
            chartMenu.setPadding(new Insets(20));
        
            // Add a title for the charts section
            Label chartsTitle = new Label("Visualisations Disponibles");
            chartsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            chartsTitle.setStyle("-fx-text-fill: #2c3e50;");
            chartsTitle.setPadding(new Insets(0, 0, 10, 0));
        
            // Create GridPane for buttons
            GridPane buttonGrid = new GridPane();
            buttonGrid.setHgap(15);  // Horizontal gap between buttons
            buttonGrid.setVgap(15);  // Vertical gap between buttons
            buttonGrid.setAlignment(Pos.CENTER);
        
            // Create all buttons
            Button[] buttons = {
                createButton("Graphique par Ville"),
                createButton("Graphique par Secteur d'Activité"),
                createButton("Graphique d'Expérience"),
                createButton("Graphique par Niveau d'Études"),
                createButton("Graphique par Type de Contrat"),
                createButton("Graphique par Télétravail"),
                createButton("Graphique par fonction de travail"),
                createButton("Expérience et Contrat"),
                createButton("Ville et Expérience"),
                createButton("Niveau d'Études et Télétravail"),
                createButton("Contrat et Télétravail")
            };
        
            // Set actions for buttons
            String[] chartTitles = {
                "Graphique par Ville",
                "Graphique par Secteur d'Activité",
                "Graphique d'Expérience Requise",
                "Graphique par Niveau d'Études",
                "Graphique par Type de Contrat",
                "Graphique par Télétravail",
                "Graphique par fonction de travail",
                "Graphique 7",
                "Graphique 8",
                "Graphique 9",
                "Graphique 10"
            };
        
            // Add buttons to grid, 2 columns
            for (int i = 0; i < buttons.length; i++) {
                int row = i / 2;    // Integer division for row index
                int col = i % 2;    // Remainder for column index
                
                // Set the action for each button
                final int index = i;
                buttons[i].setOnAction(e -> showChartPage(chartTitles[index]));
                
                // Modify button style for grid layout
                buttons[i].setPrefWidth(300);  // Make buttons wider
                
                // Add button to grid
                buttonGrid.add(buttons[i], col, row);
            }
        
            // Add decorative separator
            Separator separator = new Separator();
            separator.setStyle("-fx-background-color: #3498db;");
            separator.setPadding(new Insets(1, 0, 1, 0));
        
            // Add all components to the menu
            chartMenu.getChildren().addAll(chartsTitle, separator, buttonGrid);
        
            return chartMenu;
        }
        

        private void showChartPage(String chartTitle) {
            // Create text for chart page with black and bold text
            Text chartText = new Text(chartTitle);
            chartText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            chartText.setFill(javafx.scene.paint.Color.BLACK);

            // Create chart page
            StackPane chartPage = new StackPane();
            
            // Instantiate the correct ChartPage class
            ChartPage chartPageInstance = null;
            
            switch (chartTitle) {
                case "Graphique par Ville":
                    chartPageInstance = new ChartPage1();
                    break;
                case "Graphique par Secteur d'Activité":
                    chartPageInstance = new ChartPage2();
                    break;
                case "Graphique d'Expérience Requise":
                    chartPageInstance = new ChartPage3();
                    break;
                case "Graphique par Niveau d'Études":
                    chartPageInstance = new ChartPage4();
                    break;
                case "Graphique par Type de Contrat":
                    chartPageInstance = new ChartPage5();
                    break;
                case "Graphique par Télétravail":
                    chartPageInstance = new ChartPage6();
                    break;
                case "Graphique par fonction de travail":  
                    chartPageInstance = new ChartPage11();
                    break;
                case "Graphique 7":
                    chartPageInstance = new ChartPage7();
                    break;
                case "Graphique 8":
                    chartPageInstance = new ChartPage8();
                    break;
                case "Graphique 9":
                    chartPageInstance = new ChartPage9();
                    break;
                case "Graphique 10":
                    chartPageInstance = new ChartPage10();
                    break;
                default:
                    chartPageInstance = null;
                    break;
            }

            if (chartPageInstance != null) {
                VBox chartLayout = chartPageInstance.getChartPage(primaryStage, createMainMenu());
                chartPage.getChildren().add(chartLayout);
            } else {
                chartPage.getChildren().add(new Text("Graphique non disponible"));
            }

            Button backButton = createButton("Retour au menu principal");
            backButton.setOnAction(e -> {
                root.setTop(title);
                root.setCenter(new OverviewPage(primaryStage, root, title));
            });

            VBox chartLayout = new VBox(20, chartText, chartPage, backButton);
            chartLayout.setAlignment(Pos.CENTER);
            chartLayout.setPadding(new Insets(20));

            root.setTop(null);
            root.setCenter(chartLayout);
        }

        private StackPane createMainMenu() {
            VBox menuLayout = createChartNavigation();
            StackPane centeredPane = new StackPane(menuLayout);
            StackPane.setAlignment(menuLayout, Pos.CENTER);
            return centeredPane;
        }

        private Button createButton(String text) {
            Button button = new Button(text);
            button.setPrefWidth(300);
            button.setPrefHeight(45);
            button.setStyle(
                "-fx-background-color: #3457D5;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-family: Arial;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +  // Rounded corners
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);"  // Subtle shadow
            );
        
            // Enhanced hover animation
            button.setOnMouseEntered(e -> {
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
                scaleTransition.setToX(1.05);
                scaleTransition.setToY(1.05);
                scaleTransition.play();
                
                // Change background color on hover
                button.setStyle(
                    "-fx-background-color: #4668E0;" +  // Lighter blue on hover
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 14px;" +
                    "-fx-font-family: Arial;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 8;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0, 0, 3);"  // Enhanced shadow
                );
            });
        
            button.setOnMouseExited(e -> {
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
                scaleTransition.setToX(1);
                scaleTransition.setToY(1);
                scaleTransition.play();
                
                // Restore original style
                button.setStyle(
                    "-fx-background-color: #3457D5;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 14px;" +
                    "-fx-font-family: Arial;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 8;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);"
                );
            });
        
            return button;
        }
        
        private VBox createStatCard(String title, String value) {
            VBox card = new VBox();
            card.setStyle(
                "-fx-background-color: white;" +
                "-fx-padding: 20;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);"
            );
            card.setPrefWidth(250);
            
            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
            
            Label valueLabel = new Label(value);
            valueLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 24px; -fx-font-weight: bold;");
            valueLabel.setPadding(new Insets(10, 0, 0, 0));
            
            card.getChildren().addAll(titleLabel, valueLabel);
            return card;
        }
    }