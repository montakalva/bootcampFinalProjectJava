package com.example.housemanagementsystem.messages;

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
import java.security.Timestamp;
import java.util.ResourceBundle;

public class MessageController implements Initializable
{
    @FXML
    private TextField messageTitleField;
    @FXML
    private TextField messageStatusField;
    @FXML
    private TextField messageIDCommentField;
    @FXML
    private TextField messageCommentField;
    @FXML
    private TextField messageIDEditField;
    @FXML
    private TextField messagesStatusEditField;

    @FXML
    private TableView<Message> userReadMessagesTable;
    @FXML
    private TableColumn<Message, Integer> messageIDCol;
    @FXML
    private TableColumn<Message, String> messageTitleCol;
    @FXML
    private TableColumn<Message, String> messageStatusCol;
    @FXML
    private TableColumn<Message, Integer> commentOnMessageIDCol;
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

    @FXML
    public void onManagerDiscussionCreateClick(ActionEvent actionEvent) {
        try {
            String messageTitle = messageTitleField.getText().toUpperCase();
            String messageStatus = messageStatusField.getText().toUpperCase();
            Integer userID = DataRepository.getInstance().getLoggedInUserID();

            this.messageRepository.createNewMessage(messageTitle, messageStatus, userID);
            SceneController.showAlert("successfully created new discussion topic! ",
                    messageTitleField.getText() + " has been created successfully!",
                    Alert.AlertType.CONFIRMATION);
            SceneController.changeScene(actionEvent, "manager_view_messages" );
        } catch (Exception exception){
            SceneController.showAlert("Creating new message topic creation failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onOwnerDiscussionCommentClick(ActionEvent actionEvent) {
        try {
            Integer commentOnMessageID = Integer.valueOf(messageIDCommentField.getText());
            String messageComment = messageCommentField.getText();
            Integer userID = DataRepository.getInstance().getLoggedInUserID();
            Integer apartmentNo = Integer.valueOf(DataRepository.getInstance().getLoggedInUser().getApartmentNo());

            this.messageRepository.createNewComment(commentOnMessageID, messageComment, userID, apartmentNo);
            SceneController.showAlert("successfully created new comment! ",
                    messageCommentField.getText() + " has been created successfully!",
                    Alert.AlertType.CONFIRMATION);
                SceneController.changeScene(actionEvent, "owner_view_messages");
        } catch (Exception exception){
            SceneController.showAlert("Creating new comment creation failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onManagerDiscussionCommentClick(ActionEvent actionEvent) {
        try {
            Integer commentOnMessageID = Integer.valueOf(messageIDCommentField.getText());
            String messageComment = messageCommentField.getText();
            Integer userID = DataRepository.getInstance().getLoggedInUserID();
            Integer apartmentNo = Integer.valueOf(DataRepository.getInstance().getLoggedInUser().getApartmentNo());

            this.messageRepository.createNewComment(commentOnMessageID, messageComment, userID, apartmentNo);
            SceneController.showAlert("successfully created new comment! ",
                    messageCommentField.getText() + " has been created successfully!",
                    Alert.AlertType.CONFIRMATION);
                SceneController.changeScene(actionEvent, "manager_view_messages");
        } catch (Exception exception){
            SceneController.showAlert("Creating new comment creation failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onManagerDiscussionStatusEditClick(ActionEvent actionEvent){
        try{
            Integer messageID = Integer.valueOf(messageIDEditField.getText());
            String messageStatus = messagesStatusEditField.getText().toUpperCase();
            Integer userID = DataRepository.getInstance().getLoggedInUserID();

            this.messageRepository.editMessageStatus(messageID, messageStatus, userID);
            SceneController.showAlert("successfully edited status! ",
                    "Message status has been created successfully!",
                    Alert.AlertType.CONFIRMATION);
            SceneController.changeScene(actionEvent, "manager_view_messages" );
        } catch (Exception exception){
            SceneController.showAlert("Creating new comment creation failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeCol();
        } catch (Exception exception){
            System.out.println("Problem with discussion data upload");
            exception.printStackTrace();
        }
    }

    @FXML
    private void initializeCol() {
        try {
            messageIDCol.setCellValueFactory(new PropertyValueFactory<>("messageID"));
            messageTitleCol.setCellValueFactory(new PropertyValueFactory<>("messageTitle"));
            messageStatusCol.setCellValueFactory(new PropertyValueFactory<>("messageStatus"));
            commentOnMessageIDCol.setCellValueFactory(new PropertyValueFactory<>("commentOnMessageID"));
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
}