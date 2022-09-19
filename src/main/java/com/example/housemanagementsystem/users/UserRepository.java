package com.example.housemanagementsystem.users;

import com.example.housemanagementsystem.database.DBConnectionManager;
import com.example.housemanagementsystem.exceptions.UserNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository {

    private Connection connection = DBConnectionManager.getConnection();
    ObservableList<User> users = FXCollections.observableArrayList();

    public int verifyLoginData(String firstName, String lastName, String password) throws Exception {
        connection = DBConnectionManager.getConnection();

        String query = "SELECT * FROM users WHERE firstName = ? && lastName = ? && password = ? LIMIT 1;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        Integer userID = null;
        if (resultSet.next()) userID = resultSet.getInt("userID");

        //DBConnectionManager.closeConnection(resultSet, preparedStatement, connection);

        if (userID != null) return userID;

        throw new UserNotFoundException("User " + firstName + " " + lastName + " not found!");

    }


    public UserType checkUserType(Integer userID) throws Exception {
        connection = DBConnectionManager.getConnection();

        String query = "SELECT userType FROM users WHERE userID = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userID);

        ResultSet resultSet = preparedStatement.executeQuery();

        UserType userType = null;
        if (resultSet.next()) userType = UserType.valueOf(resultSet.getString("userType").toUpperCase());

        if (userType != null) return userType;

        //throw new Exception("Error occurred! Not possible to find usertype!");

        throw new UserNotFoundException("Unable to find user with id " + userID);


    }

    public void registerOwner(User user) throws Exception{
        connection = DBConnectionManager.getConnection();

        String query = "INSERT INTO users (apartmentNo, userType, firstName, lastName, password, email, phoneNumber)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getApartmentNo());
        preparedStatement.setString(2, user.getUserType().toString());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setString(6, user.getEmail());
        preparedStatement.setString(7, user.getPhoneNumber());

        int result = preparedStatement.executeUpdate();

        if (result != 1) throw new Exception("Registration failed for user with name " + user.getFirstName() + " " + user.getLastName());

    }

    public void updatePassword(Integer userID, String password) throws Exception {
        connection = DBConnectionManager.getConnection();

        String query = "UPDATE users SET password = ? WHERE userID = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, password);
        preparedStatement.setInt(2, userID);

        int result = preparedStatement.executeUpdate();

        if (result != 1) throw new Exception("Error occurred! Your password has not been updated!");


    }


    public String verifyPassword(Integer userID, String password) throws Exception{
        connection = DBConnectionManager.getConnection();

        String query = "SELECT password FROM users WHERE userID = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userID);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) password = resultSet.getString("password");

        if (password != null) return password;

        throw new Exception("Error occurred! Password has not been verified!");

    }

    public User getUserByID(int userID) throws Exception {
        connection = DBConnectionManager.getConnection();

        String query = "SELECT userID, apartmentNo, userType, firstName, lastName, password, email, phoneNumber FROM users WHERE userID = ? LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userID);

        ResultSet resultSet = statement.executeQuery();

        User user = null;

        if (resultSet.next()) {
            user = new User(
                    resultSet.getInt("userID"),
                    resultSet.getString("apartmentNo"),
                    UserType.valueOf(resultSet.getString("userType")),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("phoneNumber")
            );
        }

        //if (user == null) throw new Exception("Unable to find user with id " + userID);
        if(user == null) throw new UserNotFoundException("Unable to find user with id " + userID);
        return user;


    }

    public void updatePhoneNumber(Integer userID, String phoneNumber) throws Exception {
        connection = DBConnectionManager.getConnection();

        String query = "UPDATE users SET phoneNumber = ? WHERE userID = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, phoneNumber);
        preparedStatement.setInt(2, userID);

        int result = preparedStatement.executeUpdate();

        if (result != 1) throw new Exception("Error occurred! Your phone number has not been updated!");



    }

    public void updateEmailAddress(Integer userID, String email) throws Exception {
        connection = DBConnectionManager.getConnection();

        String query = "UPDATE users SET email = ? WHERE userID = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setInt(2, userID);

        int result = preparedStatement.executeUpdate();

        if (result != 1) throw new Exception("Error occurred! Your e-mail address has not been updated!");



    }

    public void deleteOwner(String firstName, String lastName, String apartmentNo) throws Exception {
        connection = DBConnectionManager.getConnection();

        String query = "DELETE FROM users WHERE firstName = ? AND lastName = ? AND apartmentNo = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, apartmentNo);

        int result = preparedStatement.executeUpdate();

        if (result != 1) throw new Exception("Error! Provided information is not valid! Apartment owner has not been deleted!");

    }

    public ObservableList<User> getAllOwnersFromDB() throws Exception{
        connection = DBConnectionManager.getConnection();

        users = FXCollections.observableArrayList();

        String query = "SELECT * FROM users;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            User user = new User();
            user.setUserID(resultSet.getInt("userID"));
            user.setApartmentNo(resultSet.getString("apartmentNo"));
            user.setUserType(UserType.valueOf(resultSet.getString("userType")));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
       //     user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setPhoneNumber(resultSet.getString("phoneNumber"));
            users.add(user);
        }

        return users;


    }

}
