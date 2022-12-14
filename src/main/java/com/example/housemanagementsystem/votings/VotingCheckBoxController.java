package com.example.housemanagementsystem.votings;

import com.example.housemanagementsystem.SceneController;
import com.example.housemanagementsystem.database.DataRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VotingCheckBoxController implements Initializable {

    @FXML
    private ChoiceBox votingTitleBox;
    @FXML
    private ChoiceBox votingAnswerBox;

    VotingRepository votingRepository = new VotingRepository();
    ObservableList<String> answerList;
    ObservableList<String> titleList;

    public void onOwnerAnswerClick(ActionEvent actionEvent){
        try{
            String votingTitle = String.valueOf(votingTitleBox.getSelectionModel().getSelectedItem());
            String votingAnswer = String.valueOf(votingAnswerBox.getSelectionModel().getSelectedItem());
            Integer apartmentNo =  Integer.valueOf(DataRepository.getInstance().getLoggedInUser().getApartmentNo());
            Integer userID = Integer.valueOf(DataRepository.getInstance().getLoggedInUserID());
            validateVotingAnswer(votingTitle, votingAnswer, userID);
            this.votingRepository.createNewVotingFromCheckBox(votingTitle, votingAnswer, apartmentNo, userID);
            SceneController.showAlert("successfully submitted voting answer! ",
                    "Voting has been submitted successfully!",
                    Alert.AlertType.INFORMATION);
            SceneController.changeScene(actionEvent, "owner_view_voting");
        } catch (Exception exception){
            SceneController.showAlert("Voting answer submit failed", exception.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    private ObservableList<String> setChoiceBoxVotingAnswers(){
        votingAnswerBox.getItems().addAll(
                answerList =  FXCollections.observableArrayList(
                        "YES",
                        "NO"
                ));
    return answerList;
}

private ObservableList<String> setChoiceBoxVotingTitle(){
    try{
        votingTitleBox.getItems().addAll(
        this.votingRepository.InsertVotingTopics());
    } catch (SQLException exception) {
        exception.printStackTrace();
    }
    return titleList;
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setChoiceBoxVotingTitle();
        setChoiceBoxVotingAnswers();
    }

    public void navigateToScene(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        SceneController.changeScene(actionEvent, source.getId());
    }

    private void validateVotingAnswer(String votingTitle,String votingAnswer, Integer userID) throws Exception {
        if (votingTitleBox.getValue() == null) throw new Exception("Please provide voting title!");
        if (votingAnswerBox.getValue() == null) throw new Exception("Please provide voting answer!");

    }
}

