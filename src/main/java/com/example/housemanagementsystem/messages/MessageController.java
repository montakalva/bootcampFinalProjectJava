package com.example.housemanagementsystem.messages;

import com.example.housemanagementsystem.SceneController;
import com.example.housemanagementsystem.database.DataRepository;
import com.example.housemanagementsystem.users.UserRepository;
import com.example.housemanagementsystem.users.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.security.Timestamp;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MessageController implements Initializable {
    @FXML
    private TextField messageTitleField;
    @FXML
    private TextField messageCommentField;
    @FXML
    private ChoiceBox discussionsTitleBox;
    @FXML
    private ChoiceBox messageStatusBox;

    @FXML
    private TableView<Message> userReadMessagesTable;
    @FXML
    private TableColumn<Message, Integer> messageIDCol;
    @FXML
    private TableColumn<Message, String> messageTitleCol;
    @FXML
    private TableColumn<Message, String> messageStatusCol;
    @FXML
    private TableColumn<Message, String> messageCommentCol;
    @FXML
    private TableColumn<Message, Timestamp> createdAtCol;
    @FXML
    private TableColumn<Message, Integer> userIDCol;
    @FXML
    private TableColumn<Message, Integer> apartmentNoCol;


    MessageRepository messageRepository = new MessageRepository();
    UserRepository userRepository = new UserRepository();
    ObservableList<String> discussionsList;

    @FXML
    public void onManagerDiscussionCreateClick(ActionEvent actionEvent) {
        try {
            String messageTitle = messageTitleField.getText().toUpperCase();
            String messageStatus = String.valueOf(messageStatusBox.getSelectionModel().getSelectedItem());
            Integer userID = DataRepository.getInstance().getLoggedInUserID();

            validateDiscussionCreate(messageTitle, messageStatus);
            this.messageRepository.createNewMessage(messageTitle, messageStatus, userID);
            SceneController.showAlert("successfully created new discussion topic! ",
                    messageTitleField.getText() + " has been created successfully!",
                    Alert.AlertType.INFORMATION);
            SceneController.changeScene(actionEvent, "manager_view_messages" );
        } catch (Exception exception){
            SceneController.showAlert("Creating new message topic creation failed", "Creating new message topic creation failed! " + exception.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    ObservableList<String> messageStatusList;

    private ObservableList<String> setChoiceBoxMessageStatus(){
        messageStatusBox.getItems().addAll(
                messageStatusList = FXCollections.observableArrayList(
                        "ACTIVE",
                        "PASSIVE")
        );
        return messageStatusList;
    }
    @FXML
    public void onManagerDiscussionCommentClick(ActionEvent actionEvent) {
        try {
            String messageTitle = String.valueOf(discussionsTitleBox.getSelectionModel().getSelectedItem());
            String messageComment = messageCommentField.getText();
            Integer userID = DataRepository.getInstance().getLoggedInUserID();

            validateDiscussionComment(messageTitle, messageComment);
            this.messageRepository.managerCreateNewComment(messageTitle, messageComment, userID);
            SceneController.showAlert("successfully created new comment! ",
                    messageCommentField.getText() + " has been created successfully!",
                    Alert.AlertType.CONFIRMATION);
                SceneController.changeScene(actionEvent, "manager_view_messages");
        } catch (Exception exception){
            SceneController.showAlert("Creating new comment creation failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeCol();
            setChoiceBoxDiscussionTitle();
            setChoiceBoxMessageStatus();
        } catch (Exception exception){
            System.out.println("Problem with discussion data upload");
        }
    }

    @FXML
    private void initializeCol() {
        try {
            userReadMessagesTable.setEditable(true);
            messageIDCol.setCellValueFactory(new PropertyValueFactory<>("messageID"));
            messageTitleCol.setCellValueFactory(new PropertyValueFactory<>("messageTitle"));
            messageStatusCol.setCellValueFactory(new PropertyValueFactory<>("messageStatus"));
            messageStatusCol.setCellFactory(TextFieldTableCell.forTableColumn());
            if(this.userRepository.checkUserType(DataRepository.getInstance().getLoggedInUserID()) == UserType.MANAGER){
                messageStatusCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Message, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Message, String> messageStringCellEditEvent) {
                        Message message = messageStringCellEditEvent.getRowValue();
                        message.setMessageStatus(messageStringCellEditEvent.getNewValue());
                        String messageStatus = message.getMessageStatus();
                        Integer messageID = message.getMessageID();
                        messageRepository.updateMessageStatus(messageStatus, messageID);
                    }
                });
            }
            messageCommentCol.setCellValueFactory(new PropertyValueFactory<>("messageComment"));
            createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            apartmentNoCol.setCellValueFactory(new PropertyValueFactory<>("apartmentNo"));
            userReadMessagesTable.setItems(this.messageRepository.addMessageToList());
        } catch (Exception e) {
            System.out.println("Problem at initialize columns");
        }
    }

    public void onGoBackClick(ActionEvent actionEvent) throws Exception {

        Integer userID = DataRepository.getInstance().getLoggedInUserID();
        UserType userType = this.userRepository.checkUserType(userID);

        if (userType == UserType.MANAGER) {
            SceneController.changeScene(actionEvent, "manager_view_messages");
        } else if (userType == UserType.OWNER) {
            SceneController.changeScene(actionEvent, "owner_view_messages");
        }
    }

    public void navigateToScene(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        SceneController.changeScene(actionEvent, source.getId());
    }

private ObservableList<String> setChoiceBoxDiscussionTitle(){
    try{
        discussionsTitleBox.getItems().addAll(
                this.messageRepository.InsertDiscussionsTopics());
    } catch (SQLException e) {
        System.out.println("Problem with initialize");;
    }
    return discussionsList;
}

    @FXML
    public void onCheckBoxOwnerDiscussionCommentClick(ActionEvent actionEvent) {
        try {
            String messageTitle = String.valueOf(discussionsTitleBox.getSelectionModel().getSelectedItem());
            String messageComment = messageCommentField.getText();
            Integer userID = DataRepository.getInstance().getLoggedInUserID();
            Integer apartmentNo = Integer.valueOf(DataRepository.getInstance().getLoggedInUser().getApartmentNo());

            validateDiscussionComment(messageTitle, messageComment);
            this.messageRepository.createCheckBoxNewComment(messageTitle, messageComment, userID, apartmentNo);
            SceneController.showAlert("successfully created new comment! ",
                     "Comment has been created successfully!",
                    Alert.AlertType.INFORMATION);
                SceneController.changeScene(actionEvent, "owner_view_messages");
        } catch (Exception exception){
            SceneController.showAlert("Creating new comment creation failed", exception.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    private void validateDiscussionCreate(String messageTitle, String messageStatus) throws Exception {
        if (messageTitle.isEmpty()) throw new Exception("Please provide Discussion Title!");
        if (messageStatusBox.getValue() == null) throw new Exception("Please provide Discussion Status!");
    }

    private void validateDiscussionComment(String messageTitle, String messageComment) throws Exception {
        if (discussionsTitleBox.getValue() == null) throw new Exception("Please provide Discussion Title!");
        if (messageComment.isEmpty()) throw new Exception("Please provide comment!");
    }
}