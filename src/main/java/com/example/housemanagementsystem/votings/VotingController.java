package com.example.housemanagementsystem.votings;

import com.example.housemanagementsystem.SceneController;
import com.example.housemanagementsystem.database.DataRepository;
import com.example.housemanagementsystem.users.UserRepository;
import com.example.housemanagementsystem.users.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class VotingController implements Initializable {
    @FXML
    private TextField votingTitleField;
    @FXML
    private TextField votingStatusField;
    @FXML
    private TextField votingTitleEditField;
    @FXML
    private TextField votingNewTitleField;
    @FXML
    private TextField votingDeleteField;
    @FXML
    private TextField votingTitleIDField;
    @FXML
    private TextField votingStatusEditField;
    @FXML
    private TextField votingAnswerField;
    @FXML
    private TextField votingAnswerTitleIDField;

    @FXML
    private TableColumn<Voting, Integer> votingIDCol;
    @FXML
    private TableColumn<Voting, String> votingTitleCol;
    @FXML
    private TableColumn<Voting, String> votingAnswerCol;
    @FXML
    private TableColumn<Voting, Timestamp> votingSubmitATCol;
    @FXML
    private TableColumn<Voting, String> votingStatusCol;
    @FXML
    private TableColumn<Voting, Integer> votingUserIDCol;
    @FXML
    private TableColumn<Voting, Integer> votingAnswerTitleIDFieldCol;
    @FXML
    private TableColumn<Voting, Integer> votingApartmentIDCol;
    @FXML
    private TableView<Voting> userReadVotingTable;

    VotingRepository votingRepository = new VotingRepository();
    UserRepository userRepository = new UserRepository();

    @FXML
    protected void onManagerVotingCreateClick(ActionEvent actionEvent) {
        try {
            String votingTitle = votingTitleField.getText();
            String votingStatus = votingStatusField.getText();

            this.votingRepository.createNewVoting(votingTitle, votingStatus);
            SceneController.showAlert("successfully created new voting topic! ",
                    votingTitleField.getText() + " has been created successfully!",
                    Alert.AlertType.CONFIRMATION);
            SceneController.changeScene(actionEvent, "manager_view_voting");
        } catch (Exception exception) {
            SceneController.showAlert("Creating new voting topic creation failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void onManagerVotingEditClick(ActionEvent actionEvent) {
        try {
            String votingTitle = votingTitleEditField.getText();
            String votingNewTitle = votingNewTitleField.getText();

            this.votingRepository.editVotingTitle(votingTitle, votingNewTitle);
            SceneController.showAlert("Voting topic successfully edited! ",
                    "Voting topic has been edited successfully!",
                    Alert.AlertType.CONFIRMATION);
            SceneController.changeScene(actionEvent, "manager_view_voting");

        } catch (Exception exception) {
            SceneController.showAlert("Editing voting topic failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void onManagerStatusEditClick(ActionEvent actionEvent){
        try{
            Integer votingID = Integer.parseInt(votingTitleIDField.getText());
            String votingStatus = votingStatusEditField.getText();

            this.votingRepository.editVotingStatus(votingStatus, votingID);
            SceneController.showAlert("Voting status successfully edited! ",
                    "Voting status has been edited successfully!",
                    Alert.AlertType.CONFIRMATION);
            SceneController.changeScene(actionEvent, "manager_view_voting");

        } catch (Exception exception) {
            SceneController.showAlert("Editing voting status failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void onManagerVotingDelete(ActionEvent actionEvent) {
        try {
            Integer votingID = Integer.valueOf(votingDeleteField.getText());
            this.votingRepository.deleteVotingTitle(votingID);
            SceneController.showAlert("successfully deleted voting topic! ",
                    "Voting has been Deleted successfully!",
                    Alert.AlertType.CONFIRMATION);
            SceneController.changeScene(actionEvent, "manager_view_voting");
        } catch (Exception exception) {
            SceneController.showAlert("Delete voting topic failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    protected void onOwnerAnswerClick(ActionEvent actionEvent) {
        try{

            Integer answerOnTopicID =  Integer.parseInt(votingAnswerTitleIDField.getText());
            String votingAnswer = votingAnswerField.getText();
            Integer apartmentNo =  Integer.valueOf(DataRepository.getInstance().getLoggedInUser().getApartmentNo());
            Integer userID = Integer.valueOf(DataRepository.getInstance().getLoggedInUserID());
            System.out.println(this.votingRepository.doesOwnerVotedOnVotingTitle(userID, apartmentNo, answerOnTopicID));
            if (this.votingRepository.doesOwnerVotedOnVotingTitle(userID, apartmentNo, answerOnTopicID) == false){
                this.votingRepository.createVotingAnswer(answerOnTopicID, votingAnswer, apartmentNo, userID);
                SceneController.showAlert("successfully submitted voting answer! ",
                        "Voting has been submitted successfully!",
                        Alert.AlertType.CONFIRMATION);
                SceneController.changeScene(actionEvent, "owner_view_voting");
            } else {
                SceneController.showAlert("Submit voting failed", "You already voted on this topic", Alert.AlertType.ERROR);
                SceneController.changeScene(actionEvent, "owner_view_voting");
            }
        } catch (Exception exception) {
            SceneController.showAlert("Submit voting failed", exception.getMessage() /*"Your voting submitting failed, try again "*/, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void initializeCol(){
        try {
        votingIDCol.setCellValueFactory(new PropertyValueFactory<>("votingID"));
        votingStatusCol.setCellValueFactory(new PropertyValueFactory<>("votingStatus"));
        votingTitleCol.setCellValueFactory(new PropertyValueFactory<>("votingTitle"));
        votingAnswerTitleIDFieldCol.setCellValueFactory(new PropertyValueFactory<>("answerOnTopicID"));
        votingAnswerCol.setCellValueFactory(new PropertyValueFactory<>("votingAnswer"));
        votingSubmitATCol.setCellValueFactory(new PropertyValueFactory<>("votingAt"));
        votingUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        votingApartmentIDCol.setCellValueFactory(new PropertyValueFactory<>("apartmentNo"));
        userReadVotingTable.setItems(this.votingRepository.addVotingToList());
        } catch (Exception e) {
            System.out.println("Problem at initialize columns");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeCol();
        } catch (Exception exception){
            System.out.println("Problem with voting data upload");
            exception.printStackTrace();
        }
    }

    @FXML
    protected void onGoBackClick(ActionEvent actionEvent) throws Exception{

        Integer userID = DataRepository.getInstance().getLoggedInUserID();

        UserType userType = this.userRepository.checkUserType(userID);

        if(userType == UserType.MANAGER){
            SceneController.changeScene(actionEvent, "manager_view_voting");
        }else if(userType == UserType.OWNER){
            SceneController.changeScene(actionEvent, "owner_view_voting");
        }
    }

    public void navigateToScene(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        SceneController.changeScene(actionEvent, source.getId());
    }

}

