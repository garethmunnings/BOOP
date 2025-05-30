package com.example.boop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScreenController {

    @FXML
    private void handlePlay(ActionEvent event) throws IOException {
        SoundPlayer.playSound("StartSound.wav");
        Parent newRoot = FXMLLoader.load(getClass().getResource("/com/example/boop/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(newRoot));
        stage.setTitle("BOOP");
        stage.setResizable(false);
        stage.show();
    }
}
