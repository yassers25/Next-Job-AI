package com.auth;

public class User {
    private int id; // Unique identifier for the user
    private String username; // Username for user authentication
    private String password; // Password for user authentication (should be hashed in production)
    private String email; // Email address of the user

    // Constructor
    public User(String username, String password, String email) {
        this.username = username; // Initialize the username
        this.password = password; // Initialize the password
        this.email = email; // Initialize the email
    }

    // Getters and setters
    // Getter for id
    public int getId() {
        return id; // Return the user's unique ID
    }

    // Setter for id
    public void setId(int id) {
        this.id = id; // Set the user's unique ID
    }

    // Getter for username
    public String getUsername() {
        return username; // Return the username
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username; // Set the username
    }

    // Getter for password
    public String getPassword() {
        return password; // Return the password
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password; // Set the password
    }

    // Getter for email
    public String getEmail() {
        return email; // Return the email address
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email; // Set the email address
    }
}
