package com.example.housemanagementsystem.invoices;

import com.example.housemanagementsystem.SceneController;
import com.example.housemanagementsystem.database.DataRepository;
import com.example.housemanagementsystem.users.UserRepository;
import com.example.housemanagementsystem.users.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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
    private TableColumn<Invoice, String> invoiceIssueDateCol;
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
    private TableColumn<Invoice, String> invoicePaidOnCol;

    InvoiceRepository invoiceRepository = new InvoiceRepository();
    UserRepository userRepository = new UserRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeCol();
        } catch (Exception exception){
            System.out.println("Problem with invoice data upload");
            exception.printStackTrace();
        }
    }

    public void navigateToScene(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        SceneController.changeScene(actionEvent, source.getId());
    }

    public void onManagerInvoiceCreateClick(ActionEvent actionEvent) {
        try{
            String invoiceNo = invoiceNoField.getText();
            String invoiceTitle = invoiceTitleField.getText();
            String invoiceCompany = invoiceCompanyField.getText();
            String invoiceIssueDate = invoiceIssueDateField.getText();
            String invoiceDescription = invoiceDescriptionField.getText();
            Double invoiceSubTotal = Double.valueOf(invoiceSubTotalField.getText());
            Double invoiceTax = Double.valueOf(invoiceTaxField.getText());
            Double invoiceTotalAmount = Double.valueOf(invoiceTotalAmountField.getText());
            String invoiceStatus = invoiceStatusField.getText();
            String invoicePaidOn = invoicePaidOnField.getText();

            this.invoiceRepository.createNewInvoice(invoiceNo, invoiceTitle, invoiceCompany, invoiceIssueDate, invoiceDescription, invoiceSubTotal,
                                                    invoiceTax, invoiceTotalAmount, invoiceStatus, invoicePaidOn);

            SceneController.showAlert("successfully created new invoice! ",
                    "Invoice has been created successfully!",
                    Alert.AlertType.CONFIRMATION);
            SceneController.changeScene(actionEvent, "manager_profile" );
        } catch (Exception exception){
            SceneController.showAlert("Creating new invoice failed", exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void onManagerInvoiceStatusEdit(ActionEvent actionEvent){
        try{
            Integer invoiceID = Integer.parseInt(invoiceStatusEditIDField.getText());
            String invoiceStatus = invoiceStatusEditField.getText();

            this.invoiceRepository.editInvoiceStatus(invoiceStatus, invoiceID);
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
            SceneController.showAlert("Delete invoice failed", exception.getMessage(), null);
        }
    }

    public void onGoBackClick(ActionEvent actionEvent) throws Exception{

        Integer userID = DataRepository.getInstance().getLoggedInUserID();

        UserType userType = this.userRepository.checkUserType(userID);

        if(userType == UserType.MANAGER){
            SceneController.changeScene(actionEvent, "manager_profile");
        }else if(userType == UserType.OWNER){
            SceneController.changeScene(actionEvent, "owner_profile");
        }
    }

    @FXML
    private void initializeCol(){
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

}
