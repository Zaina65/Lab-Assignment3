package com.example.labassignment3;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.HashMap;

public class LoginApp extends Application {
    private HashMap<String, String> users = new HashMap<>(); // To store username-password pairs

    @Override
    public void start(Stage primaryStage) {
        loadUsers();

        VBox loginLayout = new VBox(10);
        loginLayout.setAlignment(Pos.CENTER);

        String resourcePath = getClass().getResource("/com/example/labassignment3/background.jpg").toExternalForm();
        loginLayout.setStyle("-fx-background-image: url('" + resourcePath + "'); "
                + "-fx-background-size: cover;");


        Image titleImage = new Image(getClass().getResource("/com/example/labassignment3/title.jpg").toExternalForm());
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setFitHeight(100);
        titleImageView.setFitWidth(200);


        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");


        Label notificationLabel = new Label();


        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");


        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (users.containsKey(username) && users.get(username).equals(password)) {
                showWelcomeScreen(primaryStage, username);
            } else {
                notificationLabel.setText("Invalid username or password.");
                notificationLabel.setStyle("-fx-text-fill: red;");
            }
        });


        exitButton.setOnAction(e -> primaryStage.close());


        loginLayout.getChildren().addAll(titleImageView, usernameField, passwordField, loginButton, exitButton, notificationLabel);


        Scene loginScene = new Scene(loginLayout, 400, 300);
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }


    private void showWelcomeScreen(Stage stage, String username) {
        VBox welcomeLayout = new VBox(10);
        welcomeLayout.setAlignment(Pos.CENTER);
        welcomeLayout.setStyle("-fx-background-color: lightgreen; -fx-padding: 20;");

        Label welcomeLabel = new Label("Welcome, " + username + "!");
        Button backButton = new Button("Back");


        backButton.setOnAction(e -> start(stage));

        welcomeLayout.getChildren().addAll(welcomeLabel, backButton);
        Scene welcomeScene = new Scene(welcomeLayout, 400, 300);
        stage.setScene(welcomeScene);
    }


    private void loadUsers() {

        try (InputStream inputStream = getClass().getResourceAsStream("/com/example/labassignment3/users.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            System.err.println("Error loading users.txt. Make sure the file exists in the resources folder.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

