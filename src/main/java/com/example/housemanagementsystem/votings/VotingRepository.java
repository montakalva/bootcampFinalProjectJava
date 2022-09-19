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
    private ObservableList<String> checkBoxList;

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
        String query ="SELECT * FROM voting";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Voting voting = new Voting();
            voting.setVotingID(resultSet.getInt("votingID"));
            voting.setVotingStatus(resultSet.getString("votingStatus"));
            voting.setVotingTitle(resultSet.getString("votingTitle"));
            voting.setAnswerOnTopicID(resultSet.getInt("answerOnTopicID"));
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

    public void createVotingAnswer(Integer answerOnTopicID, String votingAnswer, Integer apartmentNo, Integer userID) throws SQLException {

            connection = DBConnectionManager.getConnection();

            String query = "INSERT INTO voting (answerOnTopicID, votingAnswer, apartmentNo, userID) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, answerOnTopicID);
            preparedStatement.setString(2, votingAnswer);
            preparedStatement.setInt(3, apartmentNo);
            preparedStatement.setInt(4, userID);

        preparedStatement.executeUpdate();
        }

        public Boolean doesOwnerVotedOnVotingTitle(Integer userID, Integer apartmentNo, Integer answerOnTopicID) throws SQLException {

        connection = DBConnectionManager.getConnection();

        String query = "SELECT votingAnswer FROM voting WHERE answerOnTopicID = ? AND userID = ? AND apartmentNo = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, answerOnTopicID);
        preparedStatement.setInt(2, apartmentNo);
        preparedStatement.setInt(3, userID);

        ResultSet resultSet = preparedStatement.executeQuery();

            String votingAnswer = null;
           if(resultSet.next()) votingAnswer = resultSet.getString("votingAnswer");
            System.out.println("After if: " + votingAnswer);
            if (votingAnswer != null) {
                return false;
            } else {
                return true;
            }
    }

    public ObservableList<String> addVotingTitleToList() throws SQLException {
        connection = DBConnectionManager.getConnection();
        String votingTitle;

        checkBoxList = FXCollections.observableArrayList();
        String query ="SELECT votingTitle FROM voting WHERE votingStatus = Active";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            votingTitle = resultSet.getString("votingTitle");
            checkBoxList.add(votingTitle);
        }
        return checkBoxList;
    }
}