package com.ui.dashboard;

import com.db.DatabaseServices;
import com.main.Main;
import com.parsers.emploi.EmploiParsers;
import com.parsers.mjobs.MjobParsers;
import com.parsers.rekrute.RekruteParsers;
import com.scrap.EmploiScrapper;
import com.scrap.MJob;
import com.scrap.RekruteScrapper;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainPage extends ScrollPane {
    private TextArea statusTextArea;
    
    public MainPage() {
        VBox content = new VBox();
        content.setSpacing(20);
        content.setPadding(new Insets(20));
        
        // Scraping Section
        VBox scrapingSection = createSection("Scraping Controls");
        GridPane scrapingButtons = new GridPane();
        scrapingButtons.setHgap(10);
        scrapingButtons.setVgap(10);
        
        Button rekruteScrapBtn = createActionButton("Scrape Rekrute", "#27ae60");
        Button emploiScrapBtn = createActionButton("Scrape Emploi", "#27ae60");
        Button mjobsScrapBtn = createActionButton("Scrape M-Jobs", "#27ae60");
        Button allScrapBtn = createActionButton("Scrape All", "#2980b9");
        
        scrapingButtons.add(rekruteScrapBtn, 0, 0);
        scrapingButtons.add(emploiScrapBtn, 1, 0);
        scrapingButtons.add(mjobsScrapBtn, 2, 0);
        scrapingButtons.add(allScrapBtn, 3, 0);
        
        scrapingSection.getChildren().add(scrapingButtons);
        
        // Parsing Section
        VBox parsingSection = createSection("Parsing Controls");
        GridPane parsingButtons = new GridPane();
        parsingButtons.setHgap(10);
        parsingButtons.setVgap(10);
        
        Button rekruteParseBtn = createActionButton("Parse Rekrute", "#e67e22");
        Button emploiParseBtn = createActionButton("Parse Emploi", "#e67e22");
        Button mjobsParseBtn = createActionButton("Parse M-Jobs", "#e67e22");
        Button allParseBtn = createActionButton("Parse All", "#d35400");
        
        parsingButtons.add(rekruteParseBtn, 0, 0);
        parsingButtons.add(emploiParseBtn, 1, 0);
        parsingButtons.add(mjobsParseBtn, 2, 0);
        parsingButtons.add(allParseBtn, 3, 0);
        
        parsingSection.getChildren().add(parsingButtons);
        
        // Database Section
        VBox dbSection = createSection("Database Controls");
        GridPane dbButtons = new GridPane();
        dbButtons.setHgap(10);
        dbButtons.setVgap(10);
        
        Button createSchemaBtn = createActionButton("Create Schema", "#8e44ad");
        Button insertDataBtn = createActionButton("Insert All Data", "#8e44ad");
        Button closeDbBtn = createActionButton("Close Database", "#c0392b");
        
        dbButtons.add(createSchemaBtn, 0, 0);
        dbButtons.add(insertDataBtn, 1, 0);
        dbButtons.add(closeDbBtn, 2, 0);
        
        dbSection.getChildren().add(dbButtons);

        //Pretreamtment
        VBox preProcessingSection = createSection("Data pre-Processing");
        GridPane preProcessingGrid = new GridPane();
        preProcessingGrid.setHgap(10);
        preProcessingGrid.setVgap(10);

        Button dataPreProcessingBtn = createActionButton("Pre-Processing", "#d35400");
        preProcessingGrid.add(dataPreProcessingBtn, 0, 0);
        preProcessingSection.getChildren().add(dataPreProcessingBtn);



        // Status Section
        VBox statusSection = createSection("Status Updates");
        
        statusTextArea = new TextArea();
        statusTextArea.setEditable(false);
        statusTextArea.setPrefRowCount(10);
        statusTextArea.setStyle(
            "-fx-control-inner-background: #f8f9fa;" +
            "-fx-font-family: 'Courier New';" +
            "-fx-font-size: 14px;"
        );
        
        statusSection.getChildren().add(statusTextArea);
        
        // Add all sections to content
        content.getChildren().addAll(scrapingSection, parsingSection, dbSection, statusSection);
        
        // Button Event Handlers
        

            setupButtonHandlers(rekruteScrapBtn, emploiScrapBtn, mjobsScrapBtn, allScrapBtn,
            rekruteParseBtn, emploiParseBtn, mjobsParseBtn, allParseBtn,
            createSchemaBtn, insertDataBtn, closeDbBtn);
        
        
        setContent(content);
        setFitToWidth(true);
        setStyle("-fx-background-color: #f8f9fa;");
    }
    
    private void setupButtonHandlers(Button... buttons) {
        // Rekrute Scraping Button
        buttons[0].setOnAction(e -> {
            buttons[0].setDisable(true);
            addStatus("Scraping Rekrute...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        RekruteScrapper.startScrapping(Main.rekrute);
                        Platform.runLater(() -> addStatus("Scraping Rekrute is completed..."));
                    } catch (InterruptedException ex) {
                        Platform.runLater(() -> addStatus("Error scraping Rekrute: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[0].setDisable(false));
            task.setOnFailed(event -> {
                buttons[0].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
    
        // Emploi Scraping Button
        buttons[1].setOnAction(e -> {
            buttons[1].setDisable(true);
            addStatus("Scraping Emploi...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        EmploiScrapper.startScrapping(Main.emploi);
                        Platform.runLater(() -> addStatus("Scraping Emploi is completed..."));
                    } catch (InterruptedException ex) {
                        Platform.runLater(() -> addStatus("Error scraping Emploi: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[1].setDisable(false));
            task.setOnFailed(event -> {
                buttons[1].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
    
        // M-Jobs Scraping Button
        buttons[2].setOnAction(e -> {
            buttons[2].setDisable(true);
            addStatus("Scraping M-Jobs...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        MJob.startScrapping(Main.mjobs);
                        Platform.runLater(() -> addStatus("Scraping M-Jobs is completed..."));
                    } catch (InterruptedException ex) {
                        Platform.runLater(() -> addStatus("Error scraping M-Jobs: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[2].setDisable(false));
            task.setOnFailed(event -> {
                buttons[2].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
    
        // Scrape All Button
        buttons[3].setOnAction(e -> {
            buttons[3].setDisable(true);
            addStatus("Starting scraping all websites...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        addStatus("Scraping Rekrute...");
                        RekruteScrapper.startScrapping(Main.rekrute);
                        Platform.runLater(() -> addStatus("Scraping Rekrute is completed..."));
    
                        addStatus("Scraping Emploi...");
                        EmploiScrapper.startScrapping(Main.emploi);
                        Platform.runLater(() -> addStatus("Scraping Emploi is completed..."));
                        
                        addStatus("Scraping M-Jobs...");
                        MJob.startScrapping(Main.mjobs);
                        Platform.runLater(() -> addStatus("Scraping M-Jobs is completed..."));
                        
                        Platform.runLater(() -> addStatus("Scraping all websites is completed..."));
                    } catch (InterruptedException ex) {
                        Platform.runLater(() -> addStatus("Error during scraping: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[3].setDisable(false));
            task.setOnFailed(event -> {
                buttons[3].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
    
        // Parse Rekrute Button
        buttons[4].setOnAction(e -> {
            buttons[4].setDisable(true);
            addStatus("Parsing Rekrute data...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        RekruteParsers.parseAll(Main.rekrute);
                        Platform.runLater(() -> addStatus("Parsing Rekrute data is completed..."));
                    } catch (Exception ex) {
                        Platform.runLater(() -> addStatus("Error parsing Rekrute data: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[4].setDisable(false));
            task.setOnFailed(event -> {
                buttons[4].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
    
        // Parse Emploi Button
        buttons[5].setOnAction(e -> {
            buttons[5].setDisable(true);
            addStatus("Parsing Emploi data...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        EmploiParsers.parseAll(Main.emploi);
                        Platform.runLater(() -> addStatus("Parsing Emploi data is completed..."));
                    } catch (Exception ex) {
                        Platform.runLater(() -> addStatus("Error parsing Emploi data: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[5].setDisable(false));
            task.setOnFailed(event -> {
                buttons[5].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
    
        // Parse M-Jobs Button
        buttons[6].setOnAction(e -> {
            buttons[6].setDisable(true);
            addStatus("Parsing M-Jobs data...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        MjobParsers.parseAll(Main.mjobs);
                        Platform.runLater(() -> addStatus("Parsing M-Jobs data is completed..."));
                    } catch (Exception ex) {
                        Platform.runLater(() -> addStatus("Error parsing M-Jobs data: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[6].setDisable(false));
            task.setOnFailed(event -> {
                buttons[6].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
    
        // Parse All Button
        buttons[7].setOnAction(e -> {
            buttons[7].setDisable(true);
            addStatus("Parsing all data...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        addStatus("Parsing Rekrute data...");
                        RekruteParsers.parseAll(Main.rekrute);
                        Platform.runLater(() -> addStatus("Parsing Rekrute data is completed..."));
                        
                        addStatus("Parsing Emploi data...");
                        EmploiParsers.parseAll(Main.emploi);
                        Platform.runLater(() -> addStatus("Parsing Emploi data is completed..."));
                        
                        addStatus("Parsing M-Jobs data...");
                        MjobParsers.parseAll(Main.mjobs);
                        Platform.runLater(() -> addStatus("Parsing M-Jobs data is completed..."));
                        
                        Platform.runLater(() -> addStatus("Parsing all data is completed..."));
                    } catch (Exception ex) {
                        Platform.runLater(() -> addStatus("Error during parsing: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[7].setDisable(false));
            task.setOnFailed(event -> {
                buttons[7].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
    
        // Create Schema Button
        buttons[8].setOnAction(e -> {
            buttons[8].setDisable(true);
            addStatus("Creating database schema...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        DatabaseServices.createDatabaseSchema();
                        Platform.runLater(() -> addStatus("Creating database schema is completed..."));
                    } catch (Exception ex) {
                        Platform.runLater(() -> addStatus("Error creating schema: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[8].setDisable(false));
            task.setOnFailed(event -> {
                buttons[8].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
    
        // Insert Data Button
        buttons[9].setOnAction(e -> {
            buttons[9].setDisable(true);
            addStatus("Inserting data...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        DatabaseServices.insertJobsList(Main.rekrute);
                        DatabaseServices.insertJobsList(Main.emploi);
                        DatabaseServices.insertJobsList(Main.mjobs);
                        Platform.runLater(() -> addStatus("Inserting data is completed..."));
                    } catch (Exception ex) {
                        Platform.runLater(() -> addStatus("Error inserting data: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[9].setDisable(false));
            task.setOnFailed(event -> {
                buttons[9].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
    
        // Close Database Button
        buttons[10].setOnAction(e -> {
            buttons[10].setDisable(true);
            addStatus("Closing database connection...");
            
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        // Add your database closing logic here
                        Platform.runLater(() -> addStatus("Database connection closed."));
                    } catch (Exception ex) {
                        Platform.runLater(() -> addStatus("Error closing database: " + ex.getMessage()));
                        
                    }
                    return null;
                }
            };
            
            task.setOnSucceeded(event -> buttons[10].setDisable(false));
            task.setOnFailed(event -> {
                buttons[10].setDisable(false);
                addStatus("Task failed: " + task.getException().getMessage());
            });
            
            new Thread(task).start();
        });
        // buttons[11].setOnAction(e -> {
        //     buttons[10].setDisable(true);
        //     addStatus("fetching data...");
        //     addStatus("Pre processing data");
            //Fetch and process data
            // Task<Void> task = new Task<>() {
            //     @Override
            //     protected Void call() throws Exception {
            //         try {
            //             // Add your database closing logic here
            //             Platform.runLater(() -> addStatus("Database connection closed."));
            //         } catch (Exception ex) {
            //             Platform.runLater(() -> addStatus("Error closing database: " + ex.getMessage()));
                        
            //         }
            //         return null;
            //     }
            // };
            
            // task.setOnSucceeded(event -> buttons[10].setDisable(false));
            // task.setOnFailed(event -> {
            //     buttons[10].setDisable(false);
            //     addStatus("Task failed: " + task.getException().getMessage());
            // });
            
            // new Thread(task).start();
        // });
    }
    
    private VBox createSection(String title) {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(15));
        section.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);"
        );
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        section.getChildren().add(titleLabel);
        return section;
    }
    
    private Button createActionButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 20;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5"
        );
        return button;
    }
    
    public void addStatus(String message) {
        Platform.runLater(() -> {
            String timestamp = java.time.LocalTime.now().toString().substring(0, 8);
            statusTextArea.appendText("[" + timestamp + "] " + message + "\n");
            statusTextArea.setScrollTop(Double.MAX_VALUE);
        });
    }
}