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
    private ObservableList<Message> observableList;

    public void createNewMessage(String messageTitle, String messageStatus, Integer userID) throws Exception {
        connection = DBConnectionManager.getConnection();

        String query = "INSERT INTO messages (messageTitle, messageStatus, userID) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, messageTitle);
        preparedStatement.setString(2, messageStatus);
        preparedStatement.setInt(3, userID);

        preparedStatement.executeUpdate();
    }

    public void createNewComment(Integer commentOnMessageID, String messageComment, Integer userID, Integer apartmentNo) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "INSERT INTO messages (commentOnMessageID, messageComment, userID, apartmentNo) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, commentOnMessageID);
        preparedStatement.setString(2, messageComment);
        preparedStatement.setInt(3, userID);
        preparedStatement.setInt(4, apartmentNo);

        preparedStatement.executeUpdate();
    }

    public void editMessageStatus(Integer messageID, String messageStatus, Integer userID) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "UPDATE messages SET messageStatus = ? WHERE messageID = ? AND userID = ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, messageStatus);
        preparedStatement.setInt(2, messageID);
        preparedStatement.setInt(3, userID);

        preparedStatement.executeUpdate();
    }

    public ObservableList<Message> addMessageToList() throws SQLException {

            connection = DBConnectionManager.getConnection();

            observableList = FXCollections.observableArrayList();
            String query ="SELECT messageID, messageTitle, messageStatus, commentOnMessageID, messageComment, createdAt, userID, apartmentNo FROM messages";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Message message = new Message();
                message.setMessageID(resultSet.getInt("messageID"));
                message.setMessageTitle(resultSet.getString("messageTitle"));
                message.setMessageStatus(resultSet.getString("messageStatus"));
                message.setCommentOnMessageID(resultSet.getInt("commentOnMessageID"));
                message.setMessageComment(resultSet.getString("messageComment"));
                message.setCreatedAt(resultSet.getTimestamp("createdAt"));
                message.setUserID(resultSet.getInt("userID"));
                message.setApartmentNo(resultSet.getInt("apartmentNo"));
                observableList.add(message);
            }
            return observableList;
        }


}
