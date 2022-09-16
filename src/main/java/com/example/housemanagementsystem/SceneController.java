package com.example.housemanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {

    public static void changeScene(ActionEvent actionEvent, String sceneName){

        String scenePath = "/view/" + sceneName + ".fxml";

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(scenePath)));
            stage.setScene(new Scene(parent, 1000, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(String title, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        //alert.setHeaderText(null);
        //alert.showAndWait(); you can force user to confirm alert before moving to the next step
        alert.show();
    }


}

