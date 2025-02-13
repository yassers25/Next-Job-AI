package com.ui.dashboard;

import com.main.Job;
import com.db.DatabaseServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;
import java.util.Objects;

public class SearchPage extends VBox {
    private TableView<Job> jobTable;
    private TextField searchField;
    private ComboBox<String> activitySectorFilter;
    private ComboBox<String> cityFilter;
    private ComboBox<String> contractTypeFilter;
    private ComboBox<String> experienceFilter;
    private ComboBox<String> studyLevelFilter;
    private ComboBox<String> functionFilter;
    private ComboBox<String> remoteWorkFilter;
    private ComboBox<String> siteWebFilter;
    private Label resultCountLabel;
    private FilteredList<Job> filteredData;
    private final DatabaseServices dbServices;

    public SearchPage() {
        dbServices = new DatabaseServices();
        setupUI();
        loadData();
    }

    private void setupUI() {
        setPadding(new Insets(20));
        setSpacing(20);
        setStyle("-fx-background-color: #f8f9fa;");

        // Search Section
        VBox searchSection = new VBox(15);
        searchSection.setStyle("-fx-background-color: white; -fx-padding: 20; " +
                             "-fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);");

        Label searchTitle = new Label("Job Search");
        searchTitle.setFont(Font.font("System", FontWeight.BOLD, 24));
        searchTitle.setStyle("-fx-text-fill: #2c3e50;");
        
        searchField = new TextField();
        searchField.setPromptText("Search across all fields...");
        searchField.setStyle("-fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5; " +
                           "-fx-border-color: #e0e0e0; -fx-background-color: white;");
        searchField.setPrefWidth(300);

        // Result counter
        resultCountLabel = new Label("2184 results found");
        resultCountLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        resultCountLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Filters Section
        TitledPane filtersPane = createFiltersPane();

        // Setup Table
        setupJobTable();
        VBox tableContainer = new VBox(10);
        tableContainer.setStyle("-fx-background-color: white; -fx-padding: 20; " +
                              "-fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);");
        tableContainer.getChildren().addAll(jobTable);

        // Add all components
        searchSection.getChildren().addAll(searchTitle, searchField, filtersPane, resultCountLabel);
        getChildren().addAll(searchSection, tableContainer);
        VBox.setVgrow(tableContainer, Priority.ALWAYS);

        // Setup filter listeners
        setupFilterListeners();
    }

    private TitledPane createFiltersPane() {
        VBox filtersContent = new VBox(15);
        filtersContent.setPadding(new Insets(10));

        // Initialize filter comboboxes with styling
        String comboStyle = "-fx-background-color: white; -fx-border-color: #e0e0e0; " +
                          "-fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;";

        activitySectorFilter = createStyledComboBox("Select Activity Sector", comboStyle);
        cityFilter = createStyledComboBox("Select City", comboStyle);
        contractTypeFilter = createStyledComboBox("Select Contract Type", comboStyle);
        experienceFilter = createStyledComboBox("Select Experience", comboStyle);
        studyLevelFilter = createStyledComboBox("Select Study Level", comboStyle);
        functionFilter = createStyledComboBox("Select Function", comboStyle);
        remoteWorkFilter = createStyledComboBox("Select Remote Work", comboStyle);
        siteWebFilter = createStyledComboBox("Select Website", comboStyle);

        // Create grid for filters
        GridPane filterGrid = new GridPane();
        filterGrid.setHgap(10);
        filterGrid.setVgap(10);

        // Add filters to grid with labels
        int row = 0;
        addFilterToGrid(filterGrid, "Sector:", activitySectorFilter, 0, row);
        addFilterToGrid(filterGrid, "City:", cityFilter, 1, row++);
        addFilterToGrid(filterGrid, "Function:", functionFilter, 0, row);
        addFilterToGrid(filterGrid, "Contract:", contractTypeFilter, 1, row++);
        addFilterToGrid(filterGrid, "Experience:", experienceFilter, 0, row);
        addFilterToGrid(filterGrid, "Education:", studyLevelFilter, 1, row++);
        addFilterToGrid(filterGrid, "Remote:", remoteWorkFilter, 0, row);
        addFilterToGrid(filterGrid, "Website:", siteWebFilter, 1, row++);
        // Reset Filters button
        Button resetButton = new Button("Reset Filters");
        resetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                           "-fx-padding: 8 15; -fx-background-radius: 5;");
        resetButton.setOnAction(e -> resetFilters());

        filtersContent.getChildren().addAll(filterGrid, resetButton);

        TitledPane filtersPane = new TitledPane("Filters", filtersContent);
        filtersPane.setStyle("-fx-border-color: transparent;");
        return filtersPane;
    }

    private ComboBox<String> createStyledComboBox(String prompt, String style) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(prompt);
        comboBox.setStyle(style);
        comboBox.setMaxWidth(Double.MAX_VALUE);
        return comboBox;
    }

    private void addFilterToGrid(GridPane grid, String label, ComboBox<String> comboBox, int col, int row) {
        Label filterLabel = new Label(label);
        filterLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #566573;");
        
        VBox filterContainer = new VBox(5);
        filterContainer.getChildren().addAll(filterLabel, comboBox);
        
        grid.add(filterContainer, col, row);
        
        // Set column constraints for even spacing
        if (grid.getColumnConstraints().isEmpty()) {
            ColumnConstraints column1 = new ColumnConstraints();
            ColumnConstraints column2 = new ColumnConstraints();
            column1.setPercentWidth(50);
            column2.setPercentWidth(50);
            grid.getColumnConstraints().addAll(column1, column2);
        }
    }

    private void setupJobTable() {
        jobTable = new TableView<>();
        jobTable.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; " +
                         "-fx-border-radius: 5; -fx-background-radius: 5;");

        // Add all columns
        TableColumn<Job, String> websiteCol = createColumn("Website", "siteWeb", 150);
        TableColumn<Job, String> titleCol = createColumn("Title", "jobTitle", 200);
        TableColumn<Job, String> sectorCol = createColumn("Sector", "activitySector", 150);
        TableColumn<Job, String> functionCol = createColumn("Function", "function", 150);
        TableColumn<Job, String> locationCol = createColumn("Location", "city", 100);
        TableColumn<Job, String> contractCol = createColumn("Contract", "contractType", 100);
        TableColumn<Job, String> remoteCol = createColumn("Remote", "remoteWork", 100);
        TableColumn<Job, String> experienceCol = createColumn("Experience", "requiredExperience", 100);
        TableColumn<Job, String> educationCol = createColumn("Education", "studyLevel", 100);
        TableColumn<Job, String> publishedCol = createColumn("Published", "publicationDate", 100);
        TableColumn<Job, String> deadlineCol = createColumn("Deadline", "applyBefore", 100);
        TableColumn<Job, String> profileCol = createColumn("Profile", "searchedProfile", 150);
        TableColumn<Job, String> jobUrlCol = createColumn("Job URL", "jobPageUrl", 150);
        TableColumn<Job, String> jobDescCol = createColumn("Job Description", "jobDescription", 300);
        TableColumn<Job, String> softSkillsCol = createColumn("Soft Skills", "softSkills", 150);
        TableColumn<Job, String> imageUrlCol = createColumn("Image URL", "imageUrl", 150);
        TableColumn<Job, String> addressCol = createColumn("Address", "entrepriseAddress", 200);
        TableColumn<Job, String> companyDescCol = createColumn("Company Description", "entrepriseDescription", 300);
        TableColumn<Job, String> companyCol = createColumn("Company", "entreprise", 150);
        TableColumn<Job, String> salaryCol = createColumn("Salary", "salary", 100);
        TableColumn<Job, String> regionCol = createColumn("Region", "region", 100);
        TableColumn<Job, String> hardSkillsCol = createColumn("Hard Skills", "hardSkills", 150);
        TableColumn<Job, String> languageCol = createColumn("Language", "language", 100);
        TableColumn<Job, String> languageLevelCol = createColumn("Language Level", "languageLevel", 100);

        jobTable.getColumns().addAll(
            websiteCol , titleCol, companyCol, sectorCol, functionCol, locationCol,
            contractCol, remoteCol, experienceCol, educationCol,
            publishedCol, deadlineCol, profileCol, 
             addressCol, imageUrlCol,
            jobUrlCol, companyDescCol, jobDescCol,hardSkillsCol, softSkillsCol,languageLevelCol,languageCol,salaryCol,regionCol );

        jobTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Double-click handler for job details
        jobTable.setRowFactory(tv -> {
            TableRow<Job> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    showJobDetails(row.getItem());
                }
            });
            return row;
        });
    }

    
    private TableColumn<Job, String> createColumn(String title, String property, double width) {
        TableColumn<Job, String> column = new TableColumn<>(title);
        column.setCellValueFactory(cellData -> {
            Job job = cellData.getValue();
            String value = "";
            try {
                value = (String) Job.class.getMethod("get" + property.substring(0, 1).toUpperCase() + property.substring(1))
                    .invoke(job);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String finalValue = value;
            return javafx.beans.binding.Bindings.createStringBinding(() -> finalValue);
        });
        column.setPrefWidth(width);
        return column;
    }

    private void loadData() {
        List<Job> jobs = DatabaseServices.selectAll();
        ObservableList<Job> jobData = FXCollections.observableArrayList(jobs);
        filteredData = new FilteredList<>(jobData, p -> true);
        jobTable.setItems(filteredData);

        // Load filter options
        loadFilterOptions(jobData);
    }

    private void loadFilterOptions(ObservableList<Job> jobs) {
        jobs.stream().map(Job::getActivitySector)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .forEach(sector -> activitySectorFilter.getItems().add(sector));

        jobs.stream().map(Job::getCity)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .forEach(city -> cityFilter.getItems().add(city));

        jobs.stream().map(Job::getContractType)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .forEach(type -> contractTypeFilter.getItems().add(type));

        jobs.stream().map(Job::getRequiredExperience)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .forEach(exp -> experienceFilter.getItems().add(exp));

        jobs.stream().map(Job::getStudyLevel)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .forEach(level -> studyLevelFilter.getItems().add(level));

        jobs.stream().map(Job::getFunction)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .forEach(function -> functionFilter.getItems().add(function));

        jobs.stream().map(Job::getRemoteWork)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .forEach(remote -> remoteWorkFilter.getItems().add(remote));

        jobs.stream().map(Job::getSiteWeb)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .forEach(site -> siteWebFilter.getItems().add(site));
    }

    private void setupFilterListeners() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        activitySectorFilter.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        cityFilter.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        contractTypeFilter.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        experienceFilter.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        studyLevelFilter.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        functionFilter.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        remoteWorkFilter.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        siteWebFilter.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter());
    }

    private void updateFilter() {
        filteredData.setPredicate(job -> {
            boolean matches = true;
            String searchText = searchField.getText().toLowerCase();

            // Text search across all relevant fields
            if (searchText != null && !searchText.isEmpty()) {
                matches = searchInAllFields(job, searchText);
            }

            // Apply combobox filters
            if (matches && activitySectorFilter.getValue() != null) {
                matches = job.getActivitySector() != null && 
                         job.getActivitySector().equals(activitySectorFilter.getValue());
            }
            if (matches && cityFilter.getValue() != null) {
                matches = job.getCity() != null && 
                         job.getCity().equals(cityFilter.getValue());
            }
            if (matches && contractTypeFilter.getValue() != null) {
                matches = job.getContractType() != null && 
                         job.getContractType().equals(contractTypeFilter.getValue());
            }
            if (matches && experienceFilter.getValue() != null) {
                matches = job.getRequiredExperience() != null && 
                         job.getRequiredExperience().equals(experienceFilter.getValue());
            }
            if (matches && studyLevelFilter.getValue() != null) {
                matches = job.getStudyLevel() != null && 
                         job.getStudyLevel().equals(studyLevelFilter.getValue());
            }
            if (matches && functionFilter.getValue() != null) {
                matches = job.getFunction() != null && 
                         job.getFunction().equals(functionFilter.getValue());
            }
            if (matches && remoteWorkFilter.getValue() != null) {
                matches = job.getRemoteWork() != null && 
                         job.getRemoteWork().equals(remoteWorkFilter.getValue());
            }
            if (matches && siteWebFilter.getValue() != null) {
                matches = job.getSiteWeb() != null && 
                         job.getSiteWeb().equals(siteWebFilter.getValue());
            }

            return matches;
        });

        // Update result count
        updateResultCount();
    }

    private boolean searchInAllFields(Job job, String searchText) {
        return (job.getJobTitle() != null && job.getJobTitle().toLowerCase().contains(searchText)) ||
               (job.getEntreprise() != null && job.getEntreprise().toLowerCase().contains(searchText)) ||
               (job.getActivitySector() != null && job.getActivitySector().toLowerCase().contains(searchText)) ||
               (job.getFunction() != null && job.getFunction().toLowerCase().contains(searchText)) ||
               (job.getRequiredExperience() != null && job.getRequiredExperience().toLowerCase().contains(searchText)) ||
               (job.getStudyLevel() != null && job.getStudyLevel().toLowerCase().contains(searchText)) ||
               (job.getContractType() != null && job.getContractType().toLowerCase().contains(searchText)) ||
               (job.getSearchedProfile() != null && job.getSearchedProfile().toLowerCase().contains(searchText)) ||
               (job.getRemoteWork() != null && job.getRemoteWork().toLowerCase().contains(searchText)) ||
               (job.getCity() != null && job.getCity().toLowerCase().contains(searchText)) ||
               (job.getJobDescription() != null && job.getJobDescription().toLowerCase().contains(searchText)) ||
               (job.getSiteWeb() != null && job.getSiteWeb().toLowerCase().contains(searchText)) ||
               (job.getEntrepriseDescription() != null && job.getEntrepriseDescription().toLowerCase().contains(searchText)) ||
               (job.getSoftSkills() != null && job.getSoftSkills().toLowerCase().contains(searchText)) ||
               (job.getHardSkills() != null && job.getHardSkills().toLowerCase().contains(searchText)) ||
               (job.getLanguage() != null && job.getLanguage().toLowerCase().contains(searchText));
    }

    private void updateResultCount() {
        int count = (int) filteredData.stream().count();
        resultCountLabel.setText(count + " results found");
    }

    
    private void resetFilters() {
        searchField.clear();
        activitySectorFilter.setValue(null);
        cityFilter.setValue(null);
        contractTypeFilter.setValue(null);
        experienceFilter.setValue(null);
        studyLevelFilter.setValue(null);
        functionFilter.setValue(null);
        remoteWorkFilter.setValue(null);
        siteWebFilter.setValue(null);
    }

    
    private void showJobDetails(Job job) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Job Details");
        dialog.setHeaderText(job.getJobTitle() + " at " + job.getEntreprise());

        DialogPane dialogPane = dialog.getDialogPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

    
        content.getChildren().addAll(
            createDetailSection("Title", job.getJobTitle()),
            createDetailSection("Company", job.getEntreprise()),
            createDetailSection("Company Description", job.getEntrepriseDescription()),
            createDetailSection("Location", String.format("%s%s%s", 
                job.getCity() != null ? job.getCity() : "",
                job.getRegion() != null ? ", " + job.getRegion() : "",
                job.getEntrepriseAddress() != null ? "\n" + job.getEntrepriseAddress() : "")),
            createDetailSection("Contract", job.getContractType()),
            createDetailSection("Remote Work", job.getRemoteWork()),
            createDetailSection("Required Experience", job.getRequiredExperience()),
            createDetailSection("Education", job.getStudyLevel()),
            createDetailSection("Function", job.getFunction()),
            createDetailSection("Sector", job.getActivitySector()),
            createDetailSection("Skills Required", 
                "Hard Skills: " + (job.getHardSkills() != null ? job.getHardSkills() : "Not specified") + "\n" +
                "Soft Skills: " + (job.getSoftSkills() != null ? job.getSoftSkills() : "Not specified")),
            createDetailSection("Languages", 
                (job.getLanguage() != null ? job.getLanguage() : "Not specified") + 
                (job.getLanguageLevel() != null ? " - Level: " + job.getLanguageLevel() : "")),
            createDetailSection("Salary", job.getSalary() != null ? job.getSalary().toString() : "Not specified"),
            createDetailSection("Description", job.getJobDescription()),
            createDetailSection("Searched Profile", job.getSearchedProfile()),
            createDetailSection("Website", job.getSiteWeb()),
            createDetailSection("Job Page", job.getJobPageUrl()),
            createDetailSection("Publication Date", job.getPublicationDate()),
            createDetailSection("Apply Before", job.getApplyBefore())
        );

        // Create ScrollPane for vertical scrolling
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);
        scrollPane.setPrefWidth(600);

        dialogPane.setContent(scrollPane);
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);

        dialog.showAndWait();
    }

    private VBox createDetailSection(String label, String value) {
        VBox section = new VBox(5);
        Label titleLabel = new Label(label);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        Label contentLabel = new Label(value != null ? value : "Not specified");
        contentLabel.setWrapText(true);
        section.getChildren().addAll(titleLabel, contentLabel);
        return section;
    }
}
