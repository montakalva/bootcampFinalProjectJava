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
    }

    public ObservableList<Measurement> addMeasurementToList() {
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

    public void updateColdWaterMeasurementCurrent(Double coldWaterMeasurementCurrent, Integer measurementID) {
        connection = DBConnectionManager.getConnection();
        try{
            String query = "UPDATE waterMeasurements SET coldWaterMeasurementCurrent = ? WHERE measurementID = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDouble(1, coldWaterMeasurementCurrent);
            preparedStatement.setInt(2, measurementID);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateColdWaterConsumption(Double coldWaterConsumption, Integer measurementID) {
        connection = DBConnectionManager.getConnection();
        try{
            String query = "UPDATE waterMeasurements SET coldWaterConsumption = ? WHERE measurementID = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDouble(1, coldWaterConsumption);
            preparedStatement.setInt(2, measurementID);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateHotWaterMeasurementCurrent(Double hotWaterMeasurementCurrent, Integer measurementID) {
        connection = DBConnectionManager.getConnection();
        try{
            String query = "UPDATE waterMeasurements SET hotWaterMeasurementCurrent = ? WHERE measurementID = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDouble(1, hotWaterMeasurementCurrent);
            preparedStatement.setInt(2, measurementID);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateHotWaterConsumption(Double hotWaterConsumption, Integer measurementID) {
        connection = DBConnectionManager.getConnection();
        try{
            String query = "UPDATE waterMeasurements SET hotWaterConsumption = ? WHERE measurementID = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDouble(1, hotWaterConsumption);
            preparedStatement.setInt(2, measurementID);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}

