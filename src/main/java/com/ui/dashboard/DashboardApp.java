package com.ui.dashboard;
import com.auth.LoginPage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashboardApp extends Application {
    private BorderPane root;
    private MainPage mainPage;
    private OverviewPage overviewPage;
    private SearchPage searchPage;
    private JobFilterApp jobFilterApp;
    private Button mainButton;
    private Button overviewButton;
    private Button searchButton;
    private Button AIButton;
    private Button deconnexionButton; // Added button for Déconnexion
    private Text title;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        
        // Create the title
        title = new Text("Visualisation des Données");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.BLACK);
        
        // Initialize pages
        mainPage = new MainPage();
        overviewPage = new OverviewPage(primaryStage, root, title);
        searchPage = new SearchPage();
        jobFilterApp = new JobFilterApp();
        
        // Create and setup navigation
        VBox navigation = createNavigation(primaryStage); // Pass primaryStage to navigation creation
        root.setLeft(navigation);
        
        // Set initial content
        root.setCenter(overviewPage);
        
        // Create scene and configure stage
        Scene scene = new Scene(root, 1200, 800);
        scene.setFill(Color.LIGHTSKYBLUE); // Match the background color from MainInterface
        
        primaryStage.setTitle("Job Scraper Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createNavigation(Stage primaryStage) {
        VBox navigation = new VBox();
        navigation.setStyle(
            "-fx-background-color: #2c3e50;" +
            "-fx-padding: 20;" +
            "-fx-pref-width: 200;"
        );
        navigation.setSpacing(10);
        
        // Dashboard title
        Label dashboardTitle = new Label("Dashboard");
        dashboardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        dashboardTitle.setTextFill(Color.WHITE);
        dashboardTitle.setPadding(new Insets(0, 0, 20, 0));
        
        // Navigation buttons
        overviewButton = createNavButton("Overview", true);
        mainButton = createNavButton("Main Controls", false);
        searchButton = createNavButton("Search", false);
        AIButton = createNavButton("Machine Learning", false);
        deconnexionButton = createNavButton("Déconnexion", false); // Create Déconnexion button

        AIButton.setOnAction(e -> {
            setActiveButton(AIButton);
            root.setCenter(jobFilterApp);
        });
        
        // Add button handlers
        overviewButton.setOnAction(e -> {
            setActiveButton(overviewButton);
            overviewPage = new OverviewPage((Stage) root.getScene().getWindow(), root, title);
            root.setCenter(overviewPage);
        });
        
        mainButton.setOnAction(e -> {
            setActiveButton(mainButton);
            root.setCenter(mainPage);
        });
        
        searchButton.setOnAction(e -> {
            setActiveButton(searchButton);
            root.setCenter(searchPage);
        });
        
        deconnexionButton.setOnAction(e -> {
            // Return to LoginPage
            LoginPage loginPage = new LoginPage(primaryStage);
            loginPage.show();
        });
        
        // Add all buttons to navigation
        navigation.getChildren().addAll(
            dashboardTitle,
            overviewButton,
            mainButton,
            AIButton,
            searchButton,
            deconnexionButton
        );
        
        return navigation;
    }
    
    private Button createNavButton(String text, boolean isActive) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPadding(new Insets(10, 15, 10, 15));
        
        if (isActive) {
            setActiveButtonStyle(button);
        } else {
            setInactiveButtonStyle(button);
        }
        
        return button;
    }
    
    private void setActiveButton(Button activeButton) {
        // Reset all buttons to inactive style
        setInactiveButtonStyle(overviewButton);
        setInactiveButtonStyle(mainButton);
        setInactiveButtonStyle(searchButton);
        setInactiveButtonStyle(AIButton);
        setInactiveButtonStyle(deconnexionButton);
        
        // Set the clicked button to active style
        setActiveButtonStyle(activeButton);
    }
    
    private void setActiveButtonStyle(Button button) {
        button.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
    }
    
    private void setInactiveButtonStyle(Button button) {
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #ecf0f1;" +
            "-fx-font-size: 14px;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
