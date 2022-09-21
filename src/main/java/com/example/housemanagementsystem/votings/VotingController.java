package com.example.housemanagementsystem.votings;

import com.example.housemanagementsystem.SceneController;
import com.example.housemanagementsystem.database.DataRepository;
import com.example.housemanagementsystem.users.UserRepository;
import com.example.housemanagementsystem.users.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class VotingController implements Initializable {
    @FXML
    private TextField votingTitleField;
    @FXML
    private TextField votingNewTitleField;
    @FXML
    private TextField votingDeleteField;
    @FXML
    private TextField votingTitleIDField;
    @FXML
    private ChoiceBox votingTitleBox;
    @FXML
    private ChoiceBox votingStatusBox;
    @FXML
    private ChoiceBox votingStatusEditBox;

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
    ObservableList<String> titleList;
    ObservableList<String> statusList;

    @FXML
    protected void onManagerVotingCreateClick(ActionEvent actionEvent) {
        try {
            String votingTitle = votingTitleField.getText().toUpperCase();
            String votingStatus = String.valueOf(votingStatusBox.getSelectionModel().getSelectedItem());

            validateVotingCreate(votingTitle, votingStatus);
            this.votingRepository.createNewVoting(votingTitle, votingStatus);
            SceneController.showAlert("successfully created new voting topic! ",
                    votingTitleField.getText() + " has been created successfully!",
                    Alert.AlertType.INFORMATION);
            SceneController.changeScene(actionEvent, "manager_view_voting");
        } catch (Exception exception) {
            SceneController.showAlert("Creating new voting topic creation failed", "Creating new voting topic creation failed! " + exception.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    protected void onManagerVotingEditClick(ActionEvent actionEvent) {
        try {
            String votingTitle = String.valueOf(votingTitleBox.getSelectionModel().getSelectedItem());
            String votingNewTitle = votingNewTitleField.getText().toUpperCase();

            validateVotingEdit(votingTitle, votingNewTitle);
            this.votingRepository.editVotingTitle(votingTitle, votingNewTitle);
            SceneController.showAlert("Voting topic successfully edited! ",
                    "Voting topic has been edited successfully!",
                    Alert.AlertType.INFORMATION);
            SceneController.changeScene(actionEvent, "manager_view_voting");

        } catch (Exception exception) {
            SceneController.showAlert("Editing voting topic failed", "Editing voting topic failed! " + exception.getMessage() , Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    protected void onManagerStatusEditClick(ActionEvent actionEvent){
        try{
            Integer votingID = Integer.parseInt(votingTitleIDField.getText());
            String votingStatus = String.valueOf(votingStatusEditBox.getSelectionModel().getSelectedItem());

            validateVotingStatusEdit(votingID, votingStatus);
            this.votingRepository.editVotingStatus(votingStatus, votingID);
            SceneController.showAlert("Voting status successfully edited! ",
                    "Voting status has been edited successfully!",
                    Alert.AlertType.INFORMATION);
            SceneController.changeScene(actionEvent, "manager_view_voting");

        } catch (Exception exception) {
            SceneController.showAlert("Editing voting status failed", "Editing voting status failed! " + exception.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    protected void onManagerVotingDelete(ActionEvent actionEvent) {
        try {
            Integer votingID = Integer.valueOf(votingDeleteField.getText());

            this.votingRepository.deleteVotingTitle(votingID);
            SceneController.showAlert("successfully deleted voting topic! ",
                    "Voting has been Deleted successfully!",
                    Alert.AlertType.INFORMATION);
            SceneController.changeScene(actionEvent, "manager_view_voting");
        } catch (Exception exception) {
            SceneController.showAlert("Delete voting topic failed", "Delete voting topic failed! " + exception.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void initializeCol(){
        try {
        votingIDCol.setCellValueFactory(new PropertyValueFactory<>("votingID"));
        votingStatusCol.setCellValueFactory(new PropertyValueFactory<>("votingStatus"));
        votingTitleCol.setCellValueFactory(new PropertyValueFactory<>("votingTitle"));
        votingAnswerCol.setCellValueFactory(new PropertyValueFactory<>("votingAnswer"));
        votingSubmitATCol.setCellValueFactory(new PropertyValueFactory<>("votingAt"));
        votingUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        votingApartmentIDCol.setCellValueFactory(new PropertyValueFactory<>("apartmentNo"));
        userReadVotingTable.setItems(this.votingRepository.addVotingToList());
        } catch (Exception exception) {
            System.out.println("Problem at initialize columns");
        }
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

    private ObservableList<String> setChoiceBoxVotingStatus(){
        votingStatusBox.getItems().addAll(
                statusList =  FXCollections.observableArrayList(
                        "ACTIVE",
                        "PASSIVE"
                ));
        return statusList;
    }

    private ObservableList<String> setChoiceBoxVotingEditStatus(){
        votingStatusEditBox.getItems().addAll(
                statusList =  FXCollections.observableArrayList(
                        "ACTIVE",
                        "PASSIVE"
                ));
        return statusList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeCol();
            setChoiceBoxVotingTitle();
            setChoiceBoxVotingStatus();
            setChoiceBoxVotingEditStatus();
        } catch (Exception exception){
            System.out.println("Problem with voting data initialize");
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

    private void validateVotingCreate(String votingTitle, String votingStatus) throws Exception {
        if (votingTitle.isEmpty()) throw new Exception("Please provide voting title!");
        if (votingStatus.isEmpty()) throw new Exception("Please provide voting status!");

    }

    private void validateVotingEdit(String votingTitle, String votingNewTitle) throws Exception{
        if (votingTitle.isEmpty()) throw new Exception("Please provide voting title!");
        if (votingNewTitle.isEmpty()) throw new Exception("Please provide new voting title!");
    }

    private void validateVotingStatusEdit(Integer votingID, String votingStatus) throws Exception {
        if (votingStatus.isEmpty()) throw new Exception("Please provide voting status!");
    }
}

