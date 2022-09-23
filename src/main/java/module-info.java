module com.example.housemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.configuration;
    requires lombok;
/*    requires octopus.javafx;
    requires octopus.core;*/


    opens com.example.housemanagementsystem to javafx.fxml;
    exports com.example.housemanagementsystem;
    exports com.example.housemanagementsystem.users;
    opens com.example.housemanagementsystem.users to javafx.fxml;
    exports com.example.housemanagementsystem.database;
    exports com.example.housemanagementsystem.waterMeasurements;
    exports com.example.housemanagementsystem.apartments;
    exports com.example.housemanagementsystem.invoices;
    exports com.example.housemanagementsystem.messages;
    exports com.example.housemanagementsystem.votings;
    opens com.example.housemanagementsystem.waterMeasurements to javafx.fxml;
    opens com.example.housemanagementsystem.apartments to javafx.fxml;
    opens com.example.housemanagementsystem.database to javafx.fxml;
    opens com.example.housemanagementsystem.invoices to javafx.fxml;
    opens com.example.housemanagementsystem.messages to javafx.fxml;
    opens com.example.housemanagementsystem.votings to javafx.fxml;
}