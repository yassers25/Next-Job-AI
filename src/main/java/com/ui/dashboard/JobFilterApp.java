package com.ui.dashboard;

import java.util.ArrayList;
import java.util.HashMap;

import com.ai.DecisionTreePrediction;
import com.ai.HashMapData;
import com.ai.Model;
import com.ai.NaiveBayesPrediction;
import com.ai.RandomForestPrediction;
import com.ai.SVMPrediction;
import com.ai.SalaryRegression;
import com.db.DatabaseServices;
import com.main.TestJob;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;

public class JobFilterApp extends VBox {

    private TextArea statusTextArea;
    private static final HashMap<String, Integer> targetIndices = new HashMap<>() {{
        put("Activity Sector", 0);
        put("Required Experience", 1);
        put("Study Level", 2);
        put("Contract Type", 3);
        put("Remote Work", 4);
        put("City", 5);
        put("Salary", 6);
    }};

    // Fields for storing the selected keys
    private String activitySectorKey;
    private String requiredExperienceKey;
    private String studyLevelKey;
    private String contractTypeKey;
    private String remoteWorkKey;
    private String cityKey;
    
    // HashMaps from your second file
    private static final HashMap<String, String> sectorMap = HashMapData.sectorMap;
    private static final HashMap<String, String> experienceMap = HashMapData.experienceMap;
    private static final HashMap<String, String> studyMap = HashMapData.studyMap;
    private static final HashMap<String, String> contractMap = HashMapData.contractMap;
    private static final HashMap<String, String> remoteMap = HashMapData.remoteMap;
    private static final HashMap<String, String> cityMap = HashMapData.cityMap;

    // UI Components using HashMap.Entry to store both key and value
    private ComboBox<HashMap.Entry<String, String>> sectorCombo;
    private ComboBox<HashMap.Entry<String, String>> experienceCombo;
    private ComboBox<HashMap.Entry<String, String>> studyCombo;
    private ComboBox<HashMap.Entry<String, String>> contractCombo;
    private ComboBox<HashMap.Entry<String, String>> remoteCombo;
    private ComboBox<HashMap.Entry<String, String>> cityCombo;
    private ComboBox<String> targetFieldCombo;
    private TextArea resultArea;
    private Button submitButton;

    private void disableCorrespondingComboBox(String targetField) {
        // First enable all combo boxes
        sectorCombo.setDisable(false);
        experienceCombo.setDisable(false);
        studyCombo.setDisable(false);
        contractCombo.setDisable(false);
        remoteCombo.setDisable(false);
        cityCombo.setDisable(false);

        // Disable the combo box corresponding to the selected target field
        switch (targetField) {
            case "Activity Sector" -> sectorCombo.setDisable(true);
            case "Required Experience" -> experienceCombo.setDisable(true);
            case "Study Level" -> studyCombo.setDisable(true);
            case "Contract Type" -> contractCombo.setDisable(true);
            case "Remote Work" -> remoteCombo.setDisable(true);
            case "City" -> cityCombo.setDisable(true);
            case "Salary" -> {
                // For salary prediction, keep all combo boxes enabled
            }
        }
    }

    public JobFilterApp() {
        // Basic setup remains the same
        setSpacing(25);
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f8f9fa;");

        Label titleLabel = new Label("JMachine learning");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        VBox contentBox = new VBox(20);
        contentBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);");
        contentBox.setPadding(new Insets(25));

        GridPane filterGrid = createFilterGrid();

        // Initialize ComboBoxes with HashMap entries
        String comboStyle = "-fx-background-color: white; -fx-border-color: #e0e0e0; " +
                          "-fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5; " +
                          "-fx-pref-height: 35;";

        sectorCombo = createMapComboBox("Select Activity Sector", comboStyle, sectorMap);
        experienceCombo = createMapComboBox("Select Required Experience", comboStyle, experienceMap);
        studyCombo = createMapComboBox("Select Study Level", comboStyle, studyMap);
        contractCombo = createMapComboBox("Select Contract Type", comboStyle, contractMap);
        remoteCombo = createMapComboBox("Select Remote Work Option", comboStyle, remoteMap);
        cityCombo = createMapComboBox("Select City", comboStyle, cityMap);

        // Setup change listeners to store selected keys
        sectorCombo.setOnAction(e -> activitySectorKey = getSelectedKey(sectorCombo));
        experienceCombo.setOnAction(e -> requiredExperienceKey = getSelectedKey(experienceCombo));
        studyCombo.setOnAction(e -> studyLevelKey = getSelectedKey(studyCombo));
        contractCombo.setOnAction(e -> contractTypeKey = getSelectedKey(contractCombo));
        remoteCombo.setOnAction(e -> remoteWorkKey = getSelectedKey(remoteCombo));
        cityCombo.setOnAction(e -> cityKey = getSelectedKey(cityCombo));

        // Create target field selector
        targetFieldCombo = new ComboBox<>(FXCollections.observableArrayList(
            "Activity Sector", "Required Experience", "Study Level",
            "Contract Type", "Remote Work", "City", "Salary"
        ));
        targetFieldCombo.setStyle("-fx-background-color: white; -fx-border-color: #3498db; " +
                                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5; " +
                                "-fx-pref-height: 35; -fx-border-width: 2;");
        targetFieldCombo.setPromptText("Select Target Field");

        targetFieldCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                disableCorrespondingComboBox(newValue);
            }
        });
        

        // Add components to grid
        addToGrid(filterGrid, "Activity Sector", sectorCombo, 0, 0);
        addToGrid(filterGrid, "Required Experience", experienceCombo, 0, 1);
        addToGrid(filterGrid, "Study Level", studyCombo, 0, 2);
        addToGrid(filterGrid, "Contract Type", contractCombo, 1, 0);
        addToGrid(filterGrid, "Remote Work", remoteCombo, 1, 1);
        addToGrid(filterGrid, "City", cityCombo, 1, 2);
        addToGrid(filterGrid, "Target Field", targetFieldCombo, 1, 3);

        // Create action and results sections
        VBox actionSection = createActionSection();
        VBox resultsSection = createResultsSection();

        // Assemble the UI
        contentBox.getChildren().addAll(filterGrid, actionSection, resultsSection);
        getChildren().addAll(titleLabel, contentBox);
    }

    private ComboBox<HashMap.Entry<String, String>> createMapComboBox(
            String prompt, String style, HashMap<String, String> map) {
        ComboBox<HashMap.Entry<String, String>> combo = new ComboBox<>(
            FXCollections.observableArrayList(map.entrySet())
        );
        combo.setPromptText(prompt);
        combo.setStyle(style);
        combo.setMaxWidth(Double.MAX_VALUE);
        
        // Set up custom string converter to display only the values
        combo.setConverter(new StringConverter<>() {
            @Override
            public String toString(HashMap.Entry<String, String> entry) {
                return entry == null ? "" : entry.getValue();
            }

            @Override
            public HashMap.Entry<String, String> fromString(String string) {
                return null; // Not needed for this implementation
            }
        });
        
        return combo;
    }

    private String getSelectedKey(ComboBox<HashMap.Entry<String, String>> combo) {
        HashMap.Entry<String, String> selected = combo.getValue();
        return selected != null ? selected.getKey() : null;
    }

    private String getSelectedValue(ComboBox<HashMap.Entry<String, String>> combo) {
        HashMap.Entry<String, String> selected = combo.getValue();
        return selected != null ? selected.getValue() : null;
    }

    private String getPrediction(int targetIndex, String type) {
        ArrayList<TestJob> jobs = DatabaseServices.getAllJobs();
        
       
        if(jobs == null){
            addStatus("No job opportunities were found at this time. Please double check the database");  // Changed from resultArea
            return null;
        }
        Model predictor = type.equals("tree") ? new DecisionTreePrediction(targetIndex) 
                            :  type.equals("forest") ? new RandomForestPrediction(targetIndex)
                            :  type.equals("bayes") ? new NaiveBayesPrediction(targetIndex)
                            :  new SVMPrediction(targetIndex);
                            
        
        for (TestJob job : jobs) {
            if (job.getCity() == null || job.getCity().isEmpty() ||
                job.getActivitySector() == null || job.getActivitySector().isEmpty() || 
                job.getRequiredExperience() == null || job.getRequiredExperience().isEmpty() || 
                job.getStudyLevel() == null || job.getStudyLevel().isEmpty() || 
                job.getContractType() == null || job.getContractType().isEmpty() || 
                job.getRemoteWork() == null || job.getRemoteWork().isEmpty()) {
                continue;
            }
            
            predictor.addTrainingData(job);
        }

        try {
            predictor.trainModel();
            addStatus(predictor.evaluateModel());
            return switch (targetIndex) {
                case 0 -> predictor.predictActivitySector(
                        experienceKey(), studyLevelKey(), 
                        contractTypeKey(), remoteWorkKey(), cityKey()
                );
                case 1 -> predictor.predictExperience(
                        activitySectorKey(), studyLevelKey(),
                        contractTypeKey(), remoteWorkKey(), cityKey()
                );
                case 2 -> predictor.predictStudyLevel(
                        activitySectorKey(), experienceKey(),
                        contractTypeKey(), remoteWorkKey(), cityKey()
                );
                case 3 -> predictor.predictContractType(
                        activitySectorKey(), experienceKey(),
                        studyLevelKey(), remoteWorkKey(), cityKey()
                );
                case 4 -> predictor.predictRemoteWork(
                        activitySectorKey(), experienceKey(),
                        studyLevelKey(), contractTypeKey(), cityKey()
                );
                case 5 -> predictor.predictCity(
                        activitySectorKey(), experienceKey(),
                        studyLevelKey(), contractTypeKey(), remoteWorkKey()
                );
                default -> "Unknown target";
            }; 
        } catch (Exception e) {
            return "Prediction error: " + e.getMessage();
        }
    }

    private Double getSalaryPrediction() {
        ArrayList<TestJob> jobs = new ArrayList<>();
        jobs = DatabaseServices.getJobsWithSalary();
     
        if(jobs == null){
            addStatus("No job opportunities were found at this time. Please double check the database");  // Changed from resultArea
            return null;
        }
        Model predictor =  new SalaryRegression();
        
        for (TestJob job : jobs) {
            if (job.getCity() == null || job.getCity().isEmpty() ||
                job.getActivitySector() == null || job.getActivitySector().isEmpty() || 
                job.getRequiredExperience() == null || job.getRequiredExperience().isEmpty() || 
                job.getStudyLevel() == null || job.getStudyLevel().isEmpty() || 
                job.getContractType() == null || job.getContractType().isEmpty() || 
                job.getRemoteWork() == null || job.getRemoteWork().isEmpty()) {
                continue;
            }
            
            predictor.addTrainingData(job);
        }

        try {
            predictor.trainModel();
            return predictor.predictSalary(activitySectorKey(), experienceKey(),
                studyLevelKey(), contractTypeKey(), remoteWorkKey(), cityKey()
                );
      
        } catch (Exception e) {
            
            return null;
        }
    }
    private String activitySectorKey() {
        return activitySectorKey != null ? activitySectorKey : "";
    }

    private String experienceKey() {
        return requiredExperienceKey != null ? requiredExperienceKey : "";
    }

    private String studyLevelKey() {
        return studyLevelKey != null ? studyLevelKey : "";
    }

    private String contractTypeKey() {
        return contractTypeKey != null ? contractTypeKey : "";
    }

    private String remoteWorkKey() {
        return remoteWorkKey != null ? remoteWorkKey : "";
    }

    private String cityKey() {
        return cityKey != null ? cityKey : "";
    }

    private String getValueForPrediction(int targetIndex, String predictionKey) {
        if (predictionKey == null) return null;
        return switch (targetIndex) {
            case 0 -> sectorMap.get(predictionKey);
            case 1 -> experienceMap.get(predictionKey);
            case 2 -> studyMap.get(predictionKey);
            case 3 -> contractMap.get(predictionKey);
            case 4 -> remoteMap.get(predictionKey);
            case 5 -> cityMap.get(predictionKey);
            default -> null;
        };
    }

    private void updateDisplay() {
        String targetField = targetFieldCombo.getValue();
        if (targetField == null) {
            addStatus("Please select a target field");  // Changed from resultArea
            return;
        }

        Integer targetIndex = targetIndices.get(targetField);
        if (targetIndex == null) {
            addStatus("Invalid target field");  // Changed from resultArea
            return;
        }

        if(targetIndex == 6){
            Double predictedSalary = getSalaryPrediction();
            if(predictedSalary == null){
                addStatus("Oops! something went wrong");
                return;
            }
            
            addStatus("Predicted Salary is: "+String.format("%.2f", predictedSalary)+"DH");
            return;
        }
        String treePrediction = getPrediction(targetIndex, "tree");
        String forestPrediction = getPrediction(targetIndex, "forest");
        String bayesPredicion = getPrediction(targetIndex, "bayes");
        String SVMPrediction = getPrediction(targetIndex, "svm");

        String treeValue = getValueForPrediction(targetIndex, treePrediction);
        String forestValue = getValueForPrediction(targetIndex, forestPrediction);
        String bayesValue = getValueForPrediction(targetIndex, bayesPredicion);
        String SVMValue = getValueForPrediction(targetIndex, SVMPrediction);
        
        String displayText = String.format(
            """
            Selected Target Field: %s

            Prediction Result:
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            decision tree predicted: %s
            Random Forest predicted: %s
            Naive Bayes predicted: %s
            SVM predicted: %s
            """,
            targetField,
            treeValue,
            forestValue,
            bayesValue,
            SVMValue
        );
        
        addStatus(displayText);  
    }

    public void addStatus(String message) {
        Platform.runLater(() -> {
            String timestamp = java.time.LocalTime.now().toString().substring(0, 8);
            statusTextArea.appendText("[" + timestamp + "] " + message + "\n");
            statusTextArea.setScrollTop(Double.MAX_VALUE);
        });
    }


    // Helper methods for UI creation remain largely the same
    private GridPane createFilterGrid() {
        GridPane filterGrid = new GridPane();
        filterGrid.setHgap(20);
        filterGrid.setVgap(20);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        filterGrid.getColumnConstraints().addAll(col1, col2);
        return filterGrid;
    }

    private void addToGrid(GridPane grid, String labelText, ComboBox<?> combo, int col, int row) {
        VBox container = new VBox(5);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 13px; -fx-text-fill: #566573;");
        container.getChildren().addAll(label, combo);
        grid.add(container, col, row);
    }

    private VBox createActionSection() {
        VBox actionSection = new VBox(15);
        actionSection.setAlignment(Pos.CENTER);
        actionSection.setPadding(new Insets(20, 0, 0, 0));

        submitButton = new Button("Apply Filters");
        submitButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-padding: 10 30; " +
                            "-fx-background-radius: 5; -fx-cursor: hand;");
        submitButton.setOnAction(e -> updateDisplay());

        actionSection.getChildren().add(submitButton);
        return actionSection;
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
    private VBox createResultsSection() {
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

      
        return statusSection;
    }

    
}