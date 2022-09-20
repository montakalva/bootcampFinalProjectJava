package com.example.housemanagementsystem.waterMeasurements;

import com.example.housemanagementsystem.SceneController;
import com.example.housemanagementsystem.database.DataRepository;
import com.example.housemanagementsystem.users.UserRepository;
import com.example.housemanagementsystem.users.UserType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class MeasurementController implements Initializable{

    @FXML
    private TextField coldWaterMeasurementCurrentField;
    @FXML
    private TextField coldWaterConsumptionField;
    @FXML
    private TextField hotWaterMeasurementCurrentField;
    @FXML
    private TextField hotWaterConsumptionField;


    @FXML
    private TableView<Measurement> managerReadMeasurementTable;
    @FXML
    private TableColumn<Measurement, Integer> measurementIDCol;
    @FXML
    private TableColumn<Measurement, Double> coldWaterMeasurementCurrentCol;
    @FXML
    private TableColumn<Measurement, Double> coldWaterConsumptionCol;
    @FXML
    private TableColumn<Measurement, Double> hotWaterMeasurementCurrentCol;
    @FXML
    private TableColumn<Measurement, Double> hotWaterConsumptionCol;
    @FXML
    private TableColumn<Measurement, Timestamp> submitAtCol;
    @FXML
    private TableColumn<Measurement, Timestamp> userIDCol;
    @FXML
    private TableColumn<Measurement, Integer> apartmentNoCol;

    MeasurementRepository measurementRepository = new MeasurementRepository();
    UserRepository userRepository = new UserRepository();

    @FXML
    protected void onOwnerMeasurementsSubmitClick(ActionEvent actionEvent) {
        try {
            Double coldWaterMeasurementCurrent = Double.valueOf(coldWaterMeasurementCurrentField.getText());
            Double coldWaterConsumption = Double.valueOf(coldWaterConsumptionField.getText());
            Double hotWaterMeasurementCurrent = Double.valueOf(hotWaterMeasurementCurrentField.getText());
            Double hotWaterConsumption = Double.valueOf(hotWaterConsumptionField.getText());

            Measurement newMeasurementSubmit = new Measurement(coldWaterMeasurementCurrent, coldWaterConsumption, hotWaterMeasurementCurrent, hotWaterConsumption);
            Integer userID = DataRepository.getInstance().getLoggedInUserID();
            Integer apartmentNo = Integer.parseInt(DataRepository.getInstance().getLoggedInUser().getApartmentNo());
            this.measurementRepository.createNewWaterMeasurementSubmission(newMeasurementSubmit,userID, apartmentNo);
            SceneController.showAlert("Measurements successfully submitted! ",
                    "Your measurement has been submitted successfully!",
                    Alert.AlertType.INFORMATION);
            SceneController.changeScene(actionEvent, "owner_view_measurements");
        } catch (Exception exception) {
            SceneController.showAlert("Initialize ", "Some problem occurred, please try again!", Alert.AlertType.INFORMATION);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeManagerCol();
        } catch (Exception exception){
            System.out.println("Initialize issue");
        }
    }

    @FXML
    private void initializeManagerCol() {
        try {
            managerReadMeasurementTable.setEditable(true);
            measurementIDCol.setCellValueFactory(new PropertyValueFactory<>("measurementID"));
            coldWaterMeasurementCurrentCol.setCellValueFactory(new PropertyValueFactory<>("coldWaterMeasurementCurrent"));
            coldWaterMeasurementCurrentCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            if(this.userRepository.checkUserType(DataRepository.getInstance().getLoggedInUserID()) == UserType.MANAGER) {
                coldWaterMeasurementCurrentCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Measurement, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Measurement, Double> measurementDoubleCellEditEvent) {
                        Measurement measurement = measurementDoubleCellEditEvent.getRowValue();
                        measurement.setColdWaterMeasurementCurrent(measurementDoubleCellEditEvent.getNewValue());
                        Double coldWaterMeasurementCurrent = measurement.getColdWaterMeasurementCurrent();
                        Integer measurementID = measurement.getMeasurementID();
                        measurementRepository.updateColdWaterMeasurementCurrent(coldWaterMeasurementCurrent, measurementID);
                    }
                });
            }
            coldWaterConsumptionCol.setCellValueFactory(new PropertyValueFactory<>("coldWaterConsumption"));
            coldWaterConsumptionCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            if(this.userRepository.checkUserType(DataRepository.getInstance().getLoggedInUserID()) == UserType.MANAGER) {
                coldWaterConsumptionCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Measurement, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Measurement, Double> measurementDoubleCellEditEvent) {
                        Measurement measurement = measurementDoubleCellEditEvent.getRowValue();
                        measurement.setColdWaterConsumption(measurementDoubleCellEditEvent.getNewValue());
                        Double coldWaterConsumption = measurement.getColdWaterConsumption();
                        Integer measurementID = measurement.getMeasurementID();
                        measurementRepository.updateColdWaterConsumption(coldWaterConsumption, measurementID);
                    }
                });
            }
            hotWaterMeasurementCurrentCol.setCellValueFactory(new PropertyValueFactory<>("hotWaterMeasurementCurrent"));
            hotWaterMeasurementCurrentCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            if(this.userRepository.checkUserType(DataRepository.getInstance().getLoggedInUserID()) == UserType.MANAGER) {
                hotWaterMeasurementCurrentCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Measurement, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Measurement, Double> measurementDoubleCellEditEvent) {
                        Measurement measurement = measurementDoubleCellEditEvent.getRowValue();
                        measurement.setHotWaterMeasurementCurrent(measurementDoubleCellEditEvent.getNewValue());
                        Double hotWaterMeasurementCurrent = measurement.getHotWaterMeasurementCurrent();
                        Integer measurementID = measurement.getMeasurementID();
                        measurementRepository.updateHotWaterMeasurementCurrent(hotWaterMeasurementCurrent, measurementID);
                    }
                });
            }
            hotWaterConsumptionCol.setCellValueFactory(new PropertyValueFactory<>("hotWaterConsumption"));
            hotWaterConsumptionCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            if(this.userRepository.checkUserType(DataRepository.getInstance().getLoggedInUserID()) == UserType.MANAGER){
            hotWaterConsumptionCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Measurement, Double>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Measurement, Double> measurementDoubleCellEditEvent) {
                    Measurement measurement = measurementDoubleCellEditEvent.getRowValue();
                    measurement.setHotWaterConsumption(measurementDoubleCellEditEvent.getNewValue());
                    Double hotWaterConsumption = measurement.getHotWaterConsumption();
                    Integer measurementID = measurement.getMeasurementID();
                    measurementRepository.updateHotWaterConsumption(hotWaterConsumption, measurementID);
                }
            });
            }
            submitAtCol.setCellValueFactory(new PropertyValueFactory<>("submitAt"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            apartmentNoCol.setCellValueFactory(new PropertyValueFactory<>("apartmentNo"));
            managerReadMeasurementTable.setItems(this.measurementRepository.addMeasurementToList());
            } catch (Exception exception) {
            System.out.println("Initialize issue");
            }
    }

        public void onGoBackClick (ActionEvent actionEvent) throws Exception {

            Integer userID = DataRepository.getInstance().getLoggedInUserID();
            UserType userType = this.userRepository.checkUserType(userID);

            if (userType == UserType.MANAGER) {
                SceneController.changeScene(actionEvent, "manager_profile");
            } else if (userType == UserType.OWNER) {
                SceneController.changeScene(actionEvent, "owner_view_measurements");
            }
        }

        public void navigateToScene (ActionEvent actionEvent){
            Button source = (Button) actionEvent.getSource();
            SceneController.changeScene(actionEvent, source.getId());
        }
    }

