package com.example.housemanagementsystem.invoices;

import com.example.housemanagementsystem.SceneController;
import com.example.housemanagementsystem.database.DataRepository;
import com.example.housemanagementsystem.users.User;
import com.example.housemanagementsystem.users.UserRepository;
import com.example.housemanagementsystem.users.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {

    @FXML
    private TextField invoiceNoField;
    @FXML
    private TextField invoiceTitleField;
    @FXML
    private TextField invoiceCompanyField;
    @FXML
    private TextField invoiceIssueDateField;
    @FXML
    private TextField invoiceDescriptionField;
    @FXML
    private TextField invoiceSubTotalField;
    @FXML
    private TextField invoiceTaxField;
    @FXML
    private TextField invoiceTotalAmountField;
    @FXML
    private TextField invoiceStatusField;
    @FXML
    private TextField invoicePaidOnField;
    @FXML
    private TextField invoiceIDField;
    @FXML
    private TextField invoiceStatusEditIDField;
    @FXML
    private TextField invoiceStatusEditField;
    @FXML
    private TextField invoicePaidEditField;


    @FXML
    private TableView<Invoice> userReadInvoicesTable;
    @FXML
    private TableColumn<Invoice, Integer> invoiceIDCol;
    @FXML
    private TableColumn<Invoice, String> invoiceNoCol;
    @FXML
    private TableColumn<Invoice, String> invoiceTitleCol;
    @FXML
    private TableColumn<Invoice, String> invoiceCompanyCol;
    @FXML
    private TableColumn<Invoice, Date> invoiceIssueDateCol;
    @FXML
    private TableColumn<Invoice, String> invoiceDescriptionCol;
    @FXML
    private TableColumn<Invoice, Double> invoiceSubTotalCol;
    @FXML
    private TableColumn<Invoice, Double> invoiceTaxCol;
    @FXML
    private TableColumn<Invoice, Double> invoiceTotalAmountCol;
    @FXML
    private TableColumn<Invoice, String> invoiceStatusCol;
    @FXML
    private TableColumn<Invoice, Date> invoicePaidOnCol;

    InvoiceRepository invoiceRepository = new InvoiceRepository();
    UserRepository userRepository = new UserRepository();

    public void onManagerInvoiceCreateClick(ActionEvent actionEvent) {
        try {
            String invoiceNo = invoiceNoField.getText().toUpperCase();
            String invoiceTitle = invoiceTitleField.getText().toUpperCase();
            String invoiceCompany = invoiceCompanyField.getText().toUpperCase();
            Date invoiceIssueDate = Date.valueOf(invoiceIssueDateField.getText());
            String invoiceDescription = invoiceDescriptionField.getText();
            Double invoiceSubTotal = Double.valueOf(invoiceSubTotalField.getText());
            Double invoiceTax = Double.valueOf(invoiceTaxField.getText());
            Double invoiceTotalAmount = Double.valueOf(invoiceTotalAmountField.getText());
            String invoiceStatus = invoiceStatusField.getText().toUpperCase();
            Date invoicePaidOn = Date.valueOf(invoicePaidOnField.getText());
            validateCreateNewInvoice(invoiceNo, invoiceTitle, invoiceCompany, invoiceStatus);
            this.invoiceRepository.createNewInvoice(invoiceNo, invoiceTitle, invoiceCompany, invoiceIssueDate, invoiceDescription, invoiceSubTotal,
                    invoiceTax, invoiceTotalAmount, invoiceStatus, invoicePaidOn);

            SceneController.showAlert("successfully created new invoice! ",
                    "Invoice has been created successfully!",
                    Alert.AlertType.CONFIRMATION);
            SceneController.changeScene(actionEvent, "manager_view_invoices");
        } catch (Exception exception) {
            SceneController.showAlert("Creating new invoice failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void onManagerInvoiceStatusEdit(ActionEvent actionEvent) {
        try {
            Integer invoiceID = Integer.parseInt(invoiceStatusEditIDField.getText());
            String invoiceStatus = invoiceStatusEditField.getText().toUpperCase();
            Date invoicePaidOn = Date.valueOf(invoicePaidEditField.getText());

            this.invoiceRepository.editInvoiceStatus(invoiceStatus, invoicePaidOn, invoiceID);
            SceneController.showAlert("Invoice status successfully edited! ",
                    "Invoice status has been edited successfully!",
                    Alert.AlertType.CONFIRMATION);
            SceneController.changeScene(actionEvent, "manager_view_invoices");

        } catch (Exception exception) {
            SceneController.showAlert("Editing invoice status failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void onManagerInvoiceDelete(ActionEvent actionEvent) {
        try {
            Integer invoiceID = Integer.valueOf(invoiceIDField.getText());
            this.invoiceRepository.deleteInvoice(invoiceID);
            SceneController.showAlert("successfully deleted invoice! ",
                    "Invoices has been Deleted successfully!",
                    Alert.AlertType.CONFIRMATION);
            SceneController.changeScene(actionEvent, "manager_view_invoices");
        } catch (Exception exception) {
            SceneController.showAlert("Delete invoice failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void initializeCol() {
        try {
            invoiceIDCol.setCellValueFactory(new PropertyValueFactory<>("invoiceID"));
            invoiceNoCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
            invoiceTitleCol.setCellValueFactory(new PropertyValueFactory<>("invoiceTitle"));
            invoiceCompanyCol.setCellValueFactory(new PropertyValueFactory<>("invoiceCompany"));
            invoiceIssueDateCol.setCellValueFactory(new PropertyValueFactory<>("invoiceIssueDate"));
            invoiceDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("invoiceDescription"));
            invoiceSubTotalCol.setCellValueFactory(new PropertyValueFactory<>("invoiceSubTotal"));
            invoiceTaxCol.setCellValueFactory(new PropertyValueFactory<>("invoiceTax"));
            invoiceTotalAmountCol.setCellValueFactory(new PropertyValueFactory<>("invoiceTotalAmount"));
            invoiceStatusCol.setCellValueFactory(new PropertyValueFactory<>("invoiceStatus"));
            invoicePaidOnCol.setCellValueFactory(new PropertyValueFactory<>("invoicePaidOn"));

            userReadInvoicesTable.setItems(this.invoiceRepository.addInvoiceToList());
        } catch (Exception e) {
            System.out.println("Problem at initialize columns");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeCol();
        } catch (Exception exception) {
            System.out.println("Problem with invoice data upload");
            exception.printStackTrace();
        }
    }

    @FXML
    public void onGoBackClick(ActionEvent actionEvent) throws Exception {

        Integer userID = DataRepository.getInstance().getLoggedInUserID();
        UserType userType = this.userRepository.checkUserType(userID);

        if (userType == UserType.MANAGER) {
            SceneController.changeScene(actionEvent, "manager_view_invoices");
        } else if (userType == UserType.OWNER) {
            SceneController.changeScene(actionEvent, "owner_profile");
        }
    }

    public void navigateToScene(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        SceneController.changeScene(actionEvent, source.getId());
    }

    private void validateCreateNewInvoice(String invoiceNo, String invoiceTitle, String invoiceCompany,String invoiceStatus) throws Exception {

        if (invoiceNo.isEmpty()) throw new Exception("Please provide invoice No!");
        if (invoiceTitle.isEmpty()) throw new Exception("Please provide invoice Title!");
        if (invoiceCompany.isEmpty()) throw new Exception("Please provide company!");
        if (invoiceStatus.isEmpty()) throw new Exception("Please provide invoice status");
    }
}
/*
*     private Integer invoiceID;
    private String invoiceNo;
    private String invoiceTitle;
    private String invoiceCompany;
    private Date invoiceIssueDate;
    private String invoiceDescription;
    private double invoiceSubTotal;
    private double invoiceTax;
    private double invoiceTotalAmount;
    private String invoiceStatus;
    private Date invoicePaidOn;
}*/