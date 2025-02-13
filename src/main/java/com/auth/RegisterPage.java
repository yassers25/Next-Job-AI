package com.auth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RegisterPage {
    private Stage primaryStage;

    public RegisterPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        // Main container
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f0f8ff;"); // Light blue background

        // Title
        Label titleLabel = new Label("Create Your Account");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Input fields
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField emailField = new TextField();

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        emailField.setPromptText("Email");

        usernameField.setMaxWidth(250);
        passwordField.setMaxWidth(250);
        emailField.setMaxWidth(250);

        usernameField.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5;");
        passwordField.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5;");
        emailField.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5;");

        // Buttons
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");

        registerButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white; -fx-font-size: 14px;");
        backButton.setStyle("-fx-background-color: #d4d4d4; -fx-text-fill: #333; -fx-font-size: 14px;");

        // Message label
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        // Button container
        HBox buttonBox = new HBox(15, registerButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Event handling
        registerButton.setOnAction(e -> {
            User newUser = new User(
                usernameField.getText(),
                passwordField.getText(),
                emailField.getText()
            );
            if (AuthenticationService.registerUser(newUser)) {
                messageLabel.setStyle("-fx-text-fill: green;");
                messageLabel.setText("Registration successful!");
                // Show login page after successful registration
                LoginPage loginPage = new LoginPage(primaryStage);
                loginPage.show();
            } else {
                messageLabel.setText("Registration failed. Try again.");
            }
        });

        backButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(primaryStage);
            loginPage.show();
        });

        // Add components to root
        root.getChildren().addAll(
            titleLabel,
            usernameField,
            passwordField,
            emailField,
            buttonBox,
            messageLabel
        );

        // Create and set scene
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register");
        primaryStage.show();
    }
}
