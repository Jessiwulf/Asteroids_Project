package se233.Asteroids_Project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se233.Asteroids_Project.view.GameStage;

public class Launcher extends Application {
    //Can set window size here
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;

    @Override
    public void start(Stage primaryStage) {
        // Create the game stage
        GameStage gameStage = new GameStage(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Create the scene with the game stage
        Scene scene = new Scene(gameStage);

        // Set up the primary stage
        primaryStage.setTitle("Asteroids_Project");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Request focus for the game stage
        gameStage.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}