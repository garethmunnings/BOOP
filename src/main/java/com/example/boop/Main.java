package com.example.boop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//TODO sound design
//TODO animate movement
//TODO option to select which three kittens to turn into cats
//TODO make felines images look better

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/boop/WelcomeScreen.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("BOOP");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
