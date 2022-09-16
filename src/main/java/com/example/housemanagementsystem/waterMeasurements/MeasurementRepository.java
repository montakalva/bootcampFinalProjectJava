package com.example.housemanagementsystem.waterMeasurements;

import com.example.housemanagementsystem.database.DBConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MeasurementRepository {

    private Connection connection = DBConnectionManager.getConnection();
    private ObservableList<Measurement> observableList;

    public void createNewWaterMeasurementSubmission(Measurement newMeasurementSubmit, Integer userID, Integer apartmentNo) throws SQLException {
        connection = DBConnectionManager.getConnection();
        try {
            String query = "INSERT INTO waterMeasurements (coldWaterMeasurementCurrent, coldWaterConsumption, " +
                    "hotWaterMeasurementCurrent, hotWaterConsumption, userID, apartmentNo) " +
                    "VALUES (?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDouble(1, newMeasurementSubmit.getColdWaterMeasurementCurrent());
            preparedStatement.setDouble(2, newMeasurementSubmit.getColdWaterConsumption());
            preparedStatement.setDouble(3, newMeasurementSubmit.getHotWaterMeasurementCurrent());
            preparedStatement.setDouble(4, newMeasurementSubmit.getHotWaterConsumption());
            preparedStatement.setInt(5, userID);
            preparedStatement.setInt(6, apartmentNo);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void editWaterMeasurementSubmission(Measurement newMeasurementEdit, Integer userID, Integer measurementID) throws SQLException {
        connection = DBConnectionManager.getConnection();
        try {
            String query = "UPDATE waterMeasurements SET coldWaterMeasurementCurrent = ?, coldWaterConsumption = ?, " +
                    "hotWaterMeasurementCurrent = ?, hotWaterConsumption = ? " +
                    "WHERE userID = ? AND measurementID = ? ";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDouble(1, newMeasurementEdit.getColdWaterMeasurementCurrent());
            preparedStatement.setDouble(2, newMeasurementEdit.getColdWaterConsumption());
            preparedStatement.setDouble(3, newMeasurementEdit.getHotWaterMeasurementCurrent());
            preparedStatement.setDouble(4, newMeasurementEdit.getHotWaterConsumption());
            preparedStatement.setInt(5, userID);
            preparedStatement.setInt(6, measurementID);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ObservableList<Measurement> addMeasurementToList() throws SQLException {
        connection = DBConnectionManager.getConnection();

        try {
            observableList = FXCollections.observableArrayList();
            String query = "SELECT measurementID, coldWaterMeasurementCurrent, coldWaterConsumption, hotWaterMeasurementCurrent, " +
                    "hotWaterConsumption, submitAt, userID, apartmentNo " +
                    "FROM waterMeasurements";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Measurement measurement = new Measurement();
                measurement.setMeasurementID(resultSet.getInt("measurementID"));
                measurement.setColdWaterMeasurementCurrent(resultSet.getDouble("coldWaterMeasurementCurrent"));
                measurement.setColdWaterConsumption(resultSet.getDouble("coldWaterConsumption"));
                measurement.setHotWaterMeasurementCurrent(resultSet.getDouble("hotWaterMeasurementCurrent"));
                measurement.setHotWaterConsumption(resultSet.getDouble("hotWaterConsumption"));
                measurement.setSubmitAt(resultSet.getTimestamp("submitAt"));
                measurement.setUserID(resultSet.getInt("userID"));
                measurement.setApartmentNo(resultSet.getInt("apartmentNo"));
                observableList.add(measurement);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return observableList;
    }
}

