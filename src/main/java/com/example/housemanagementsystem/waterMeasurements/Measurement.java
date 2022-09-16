package com.example.housemanagementsystem.waterMeasurements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLData;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Measurement {

    private Integer measurementID;
    private Double coldWaterMeasurementCurrent;
    private Double coldWaterConsumption;
    private Double hotWaterMeasurementCurrent;
    private Double hotWaterConsumption;
    private Timestamp submitAt;
    private Integer userID;
    private Integer apartmentNo;
    private SQLData dueDate;
    private String unitOfMeasure;
    private Double coldWaterPrice;
    private Double hotWaterPrice;
    private Double seweragePrice;
    private Double totalPrice;

    public Measurement(Double coldWaterMeasurementCurrent, Double coldWaterConsumption, Double hotWaterMeasurementCurrent, Double hotWaterConsumption) {
        this.coldWaterMeasurementCurrent = coldWaterMeasurementCurrent;
        this.coldWaterConsumption = coldWaterConsumption;
        this.hotWaterMeasurementCurrent = hotWaterMeasurementCurrent;
        this.hotWaterConsumption = hotWaterConsumption;
    }

    public Measurement(Integer measurementID, Double coldWaterMeasurementCurrent, Double coldWaterConsumption, Double hotWaterMeasurementCurrent, Double hotWaterConsumption, Timestamp submitAt, Integer userID, Integer apartmentNo ) {
        this.measurementID = measurementID;
        this.coldWaterMeasurementCurrent = coldWaterMeasurementCurrent;
        this.coldWaterConsumption = coldWaterConsumption;
        this.hotWaterMeasurementCurrent = hotWaterMeasurementCurrent;
        this.hotWaterConsumption = hotWaterConsumption;
        this.submitAt = submitAt;
        this.apartmentNo = apartmentNo;
        this.userID = userID;
    }
}
