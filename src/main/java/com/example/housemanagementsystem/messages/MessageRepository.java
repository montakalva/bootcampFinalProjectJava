package com.example.housemanagementsystem.messages;

import com.example.housemanagementsystem.database.DBConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageRepository {

    private Connection connection = DBConnectionManager.getConnection();
    ObservableList<Message> observableList;
    ObservableList<String> discussionsList;

    public void createNewMessage(String messageTitle, String messageStatus, Integer userID) throws Exception {
        connection = DBConnectionManager.getConnection();

        String query = "INSERT INTO messages (messageTitle, messageStatus, userID) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, messageTitle);
        preparedStatement.setString(2, messageStatus);
        preparedStatement.setInt(3, userID);

        preparedStatement.executeUpdate();
    }

    public ObservableList<Message> addMessageToList() throws SQLException {
            connection = DBConnectionManager.getConnection();
            observableList = FXCollections.observableArrayList();

            String query ="SELECT * FROM messages";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setMessageID(resultSet.getInt("messageID"));
                message.setMessageTitle(resultSet.getString("messageTitle"));
                message.setMessageStatus(resultSet.getString("messageStatus"));
                message.setMessageComment(resultSet.getString("messageComment"));
                message.setCreatedAt(resultSet.getTimestamp("createdAt"));
                message.setUserID(resultSet.getInt("userID"));
                message.setApartmentNo(resultSet.getInt("apartmentNo"));
                observableList.add(message);
            }
            return observableList;
        }

    public ObservableList<String> InsertDiscussionsTopics() throws SQLException {
        discussionsList = FXCollections.observableArrayList();
        connection = DBConnectionManager.getConnection();

        String query = "SELECT messageTitle FROM messages WHERE messageStatus = 'ACTIVE'" ;
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String messageTitle = resultSet.getString("messageTitle");
            discussionsList.add(messageTitle);
        }
        return discussionsList;
    }

    public void createCheckBoxNewComment(String messageTitle, String messageComment, Integer userID, Integer apartmentNo) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "INSERT INTO messages (messageTitle, messageComment, userID, apartmentNo) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, messageTitle);
        preparedStatement.setString(2, messageComment);
        preparedStatement.setInt(3, userID);
        preparedStatement.setInt(4, apartmentNo);

        preparedStatement.executeUpdate();
    }

    public void managerCreateNewComment(String messageTitle, String messageComment, Integer userID) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "INSERT INTO messages (messageTitle, messageComment, userID) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, messageTitle);
        preparedStatement.setString(2, messageComment);
        preparedStatement.setInt(3, userID);

        preparedStatement.executeUpdate();
    }

    public void updateMessageStatus(String messageStatus, Integer messageID) {
        connection = DBConnectionManager.getConnection();
        try{
            String query = "UPDATE messages SET messageStatus = ? WHERE messageID = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, messageStatus);
            preparedStatement.setInt(2, messageID);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
