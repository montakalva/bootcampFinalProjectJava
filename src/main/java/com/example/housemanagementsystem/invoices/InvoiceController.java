package com.example.housemanagementsystem.invoices;

import com.example.housemanagementsystem.SceneController;
import com.example.housemanagementsystem.database.DataRepository;
import com.example.housemanagementsystem.users.UserRepository;
import com.example.housemanagementsystem.users.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TextField invoiceCompanyField;
    @FXML
    private TextField invoiceDescriptionField;
    @FXML
    private TextField invoiceSubTotalField;
    @FXML
    private TextField invoiceTaxField;
    @FXML
    private TextField invoiceTotalAmountField;
    @FXML
    private TextField invoiceIDField;
    @FXML
    private TextField invoiceStatusEditIDField;
    @FXML
    private ChoiceBox invoiceTitleBox;
    @FXML
    private ChoiceBox invoiceStatusBox;
    @FXML
    private ChoiceBox invoiceStatusEditBox;
    @FXML
    DatePicker datePickerIssueDate;
    @FXML
    DatePicker datePickerInvoiceDueDate;

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
    private TableColumn<Invoice, Date> invoiceDueDateCol;

    InvoiceRepository invoiceRepository = new InvoiceRepository();
    UserRepository userRepository = new UserRepository();
    ObservableList<String> invoiceTitleList;
    ObservableList<String> invoiceStatusList;

    public void onManagerInvoiceCreateClick(ActionEvent actionEvent) {
        try {
            String invoiceNo = invoiceNoField.getText().toUpperCase();
            String invoiceTitle = String.valueOf(invoiceTitleBox.getSelectionModel().getSelectedItem());
            String invoiceCompany = invoiceCompanyField.getText().toUpperCase();
            Date invoiceIssueDate = Date.valueOf(datePickerIssueDate.getValue());
            String invoiceDescription = invoiceDescriptionField.getText();
            Double invoiceSubTotal = Double.valueOf(invoiceSubTotalField.getText());
            Double invoiceTax = Double.valueOf(invoiceTaxField.getText());
            Double invoiceTotalAmount = Double.valueOf(invoiceTotalAmountField.getText());
            String invoiceStatus = String.valueOf(invoiceStatusBox.getSelectionModel().getSelectedItem());
            Date invoiceDueDate = Date.valueOf(datePickerInvoiceDueDate.getValue());
            validateCreateNewInvoice(invoiceNo, invoiceTitle, invoiceCompany, invoiceDueDate, invoiceDescription, invoiceSubTotal, invoiceTax, invoiceTotalAmount, invoiceStatus);
            this.invoiceRepository.createNewInvoice(invoiceNo, invoiceTitle, invoiceCompany, invoiceIssueDate, invoiceDescription, invoiceSubTotal,
                    invoiceTax, invoiceTotalAmount, invoiceStatus, invoiceDueDate);

            SceneController.showAlert("successfully created new invoice! ",
                    "Invoice has been created successfully!",
                    Alert.AlertType.INFORMATION);
            SceneController.changeScene(actionEvent, "manager_view_invoices");
        } catch (Exception exception) {
            SceneController.showAlert("Creating new invoice failed", "Creating new invoice failed!" + exception.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    private ObservableList<String> setChoiceBoxInvoiceType(){
        invoiceTitleBox.getItems().addAll(
                invoiceTitleList = FXCollections.observableArrayList(
                        "INCOMES",
                        "EXPENSES")
                );
        return invoiceTitleList;
    }
    private ObservableList<String> setChoiceBoxInvoiceStatus() {
        invoiceStatusBox.getItems().addAll(
                invoiceStatusList = FXCollections.observableArrayList(
                        "PAID",
                        "UNPAID")
        );
        return invoiceStatusList;
    }

    private ObservableList<String> setChoiceBoxInvoiceStatusEdit(){
        invoiceStatusEditBox.getItems().addAll(
                invoiceStatusList = FXCollections.observableArrayList(
                        "PAID",
                        "UNPAID")
        );
        return invoiceStatusList;
    }

    @FXML
    protected void onManagerInvoiceStatusEdit(ActionEvent actionEvent) {
        try {
            Integer invoiceID = Integer.parseInt(invoiceStatusEditIDField.getText());
            String invoiceStatus =  String.valueOf(invoiceStatusEditBox.getSelectionModel().getSelectedItem());
            this.invoiceRepository.editInvoiceStatus(invoiceStatus, invoiceID);

            SceneController.showAlert("Invoice status successfully edited! ",
                    "Invoice status has been edited successfully!",
                    Alert.AlertType.INFORMATION);
            SceneController.changeScene(actionEvent, "manager_view_invoices");

        } catch (Exception exception) {
            SceneController.showAlert("Editing invoice status failed", "Editing invoice status failed", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    protected void onManagerInvoiceDelete(ActionEvent actionEvent) {
        try {
            Integer invoiceID = Integer.valueOf(invoiceIDField.getText());
            this.invoiceRepository.deleteInvoice(invoiceID);

            SceneController.showAlert("successfully deleted invoice! ",
                    "Invoices has been Deleted successfully!",
                    Alert.AlertType.INFORMATION);
            SceneController.changeScene(actionEvent, "manager_view_invoices");
        } catch (Exception exception) {
            SceneController.showAlert("Delete invoice failed", "Delete invoice failed", Alert.AlertType.INFORMATION);
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
            invoiceDueDateCol.setCellValueFactory(new PropertyValueFactory<>("invoiceDueDate"));

            userReadInvoicesTable.setItems(this.invoiceRepository.addInvoiceToList());
        } catch (Exception exception) {
            System.out.println("Problem at initialize columns");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeCol();
            setChoiceBoxInvoiceType();
            setChoiceBoxInvoiceStatus();
            setChoiceBoxInvoiceStatusEdit();
        } catch (Exception exception) {
            System.out.println("Problem with invoice data upload");
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

    private void validateCreateNewInvoice(String invoiceNo,String invoiceTitle,String invoiceCompany, Date invoiceIssueDate,
                                          String invoiceDescription,Double invoiceSubTotal,Double invoiceTax,
                                          Double invoiceTotalAmount,String invoiceStatus) throws Exception {

        if (invoiceNo.isEmpty()) throw new Exception("Please provide invoice No!");
        if (invoiceTitleBox.getValue() == null) throw new Exception("Please provide invoice Title!");
        if (invoiceCompany.isEmpty()) throw new Exception("Please provide company!");
        if (invoiceDescription.isEmpty()) throw new Exception("Please provide invoice description!");
        if (invoiceStatus.isEmpty()) throw new Exception("Please provide invoice status");
        if (invoiceSubTotal >= invoiceTotalAmount) {
            throw new Exception("Please check invoice Sub Total an Total amount values");
        }
        if (invoiceTotalAmount != (invoiceSubTotal + invoiceTax)) {
            throw new Exception("Please check values of: invoice tax, subtotal, total amount");
        }
        if (invoiceSubTotal.isNaN()) throw new Exception("Please provide valid value!");
        if (invoiceTax.isNaN()) throw new Exception("Please provide valid value!");
        if (invoiceTotalAmount.isNaN()) throw new Exception("Please provide valid value!");
        if (datePickerIssueDate.getValue() == null) throw new Exception("Please provide invoice issue date!");
        if (datePickerInvoiceDueDate.getValue() == null) throw new Exception("Please provide invoice Due Date");
        if (invoiceStatusBox.getValue() == null) throw new Exception("Please provide invoice status");
    }

}
