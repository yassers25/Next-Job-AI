package com.auth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.ui.dashboard.DashboardApp;

public class LoginPage {
    private Stage primaryStage;

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        // Main container
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f0f8ff;"); // Light blue background

        // Title
        Label titleLabel = new Label("Welcome");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(250);
        usernameField.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5;");

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(250);
        passwordField.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5;");

        // Login and Register buttons
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        loginButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white; -fx-font-size: 14px;");
        registerButton.setStyle("-fx-background-color: #d4d4d4; -fx-text-fill: #333; -fx-font-size: 14px;");

        // Button container
        HBox buttonBox = new HBox(15, loginButton, registerButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Message label
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        // Event handling
        loginButton.setOnAction(e -> {
            User user = AuthenticationService.loginUser(
                usernameField.getText(),
                passwordField.getText()
            );
            if (user != null) {
                DashboardApp dashboard = new DashboardApp();
                dashboard.start(primaryStage);
            } else {
                messageLabel.setText("Invalid credentials. Please try again.");
            }
        });

        registerButton.setOnAction(e -> {
            RegisterPage registerPage = new RegisterPage(primaryStage);
            registerPage.show();
        });

        // Add components to root
        root.getChildren().addAll(
            titleLabel,
            usernameField,
            passwordField,
            buttonBox,
            messageLabel
        );

        // Create and set scene
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
}
