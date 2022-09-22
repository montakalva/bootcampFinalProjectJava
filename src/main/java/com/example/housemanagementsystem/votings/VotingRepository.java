package com.example.housemanagementsystem.votings;

import com.example.housemanagementsystem.database.DBConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VotingRepository {

    private Connection connection = DBConnectionManager.getConnection();
    private ObservableList<Voting> observableList;

    public void createNewVoting(String votingTitle, String votingStatus) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "INSERT INTO voting (votingTitle, votingStatus) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, votingTitle);
        preparedStatement.setString(2, votingStatus);

        preparedStatement.executeUpdate();
    }

    public void editVotingTitle(String votingTitle, String votingNewTitle) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "UPDATE voting SET votingTitle = ? WHERE votingTitle = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, votingNewTitle);
        preparedStatement.setString(2, votingTitle);

        preparedStatement.executeUpdate();
    }

    public void deleteVotingTitle(Integer votingID) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "DELETE FROM voting WHERE votingID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, votingID);

        if (preparedStatement.executeUpdate() == 0) {
            throw new SQLException("Could not delete. Voting with id " + votingID + " not found");
        }
    }

    public ObservableList<Voting> addVotingToList() throws SQLException {
        connection = DBConnectionManager.getConnection();
        observableList = FXCollections.observableArrayList();

        String query = "SELECT * FROM voting";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Voting voting = new Voting();
            voting.setVotingID(resultSet.getInt("votingID"));
            voting.setVotingStatus(resultSet.getString("votingStatus"));
            voting.setVotingTitle(resultSet.getString("votingTitle"));
            voting.setVotingAnswer(resultSet.getString("votingAnswer"));
            voting.setVotingAt(resultSet.getTimestamp("votingAt"));
            voting.setApartmentNo(resultSet.getInt("apartmentNo"));
            voting.setUserID(resultSet.getInt("userID"));
            observableList.add(voting);
        }
        return observableList;
    }

    public void editVotingStatus(String votingStatus, Integer votingID) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "UPDATE voting SET votingStatus = ? WHERE votingID = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, votingStatus);
        preparedStatement.setInt(2, votingID);

        preparedStatement.executeUpdate();
    }

    public void createNewVotingFromCheckBox(String votingTitle, String votingAnswer, Integer apartmentNo, Integer userID) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "INSERT INTO voting (votingTitle, votingAnswer, apartmentNo, userID) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, votingTitle);
        preparedStatement.setString(2, votingAnswer);
        preparedStatement.setInt(3, apartmentNo);
        preparedStatement.setInt(4, userID);

        preparedStatement.executeUpdate();
    }

    public ObservableList<String> InsertVotingTopics() throws SQLException {
        connection = DBConnectionManager.getConnection();
        ObservableList<String> titleList = FXCollections.observableArrayList();

        String query = "SELECT votingTitle FROM voting WHERE votingStatus = 'ACTIVE'";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String votingTitle = resultSet.getString("votingTitle");
            titleList.add(votingTitle);
        }
        return titleList;
    }
}