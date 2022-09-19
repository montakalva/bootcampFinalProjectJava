package com.example.housemanagementsystem.votings;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class VotingCheckBoxController implements Initializable {
    @FXML
    private ChoiceBox<String> choiceBoxVoting;
    @FXML
    private ChoiceBox votingTitleBox;
    @FXML
    private ChoiceBox votingAnswerBox;

    ObservableList<String> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        list.addAll("votingTitleBox", "votingAnswerBox");
        //populate the Choicebox;
        choiceBoxVoting.setValue("One");
        choiceBoxVoting.setValue("Two");
        choiceBoxVoting.setValue("Three");
        choiceBoxVoting.setItems(list);
    }



}
