package com.example.housemanagementsystem.invoices;

import com.example.housemanagementsystem.database.DBConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class InvoiceRepository {
    private Connection connection = DBConnectionManager.getConnection();
    private ObservableList<Invoice> observableList;

    public void createNewInvoice(String invoiceNo, String invoiceTitle, String invoiceCompany, Date invoiceIssueDate,
                                 String invoiceDescription, Double invoiceSubTotal, Double invoiceTax, Double invoiceTotalAmount,
                                 String invoiceStatus, Date invoiceDueDate) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "INSERT INTO invoices (invoiceNo, invoiceTitle, invoiceCompany, invoiceIssueDate, invoiceDescription, " +
                        "invoiceSubTotal, invoiceTax, invoiceTotalAmount, invoiceStatus, invoiceDueDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, invoiceNo);
        preparedStatement.setString(2, invoiceTitle);
        preparedStatement.setString(3, invoiceCompany);
        preparedStatement.setDate(4, invoiceIssueDate);
        preparedStatement.setString(5, invoiceDescription);
        preparedStatement.setDouble(6, invoiceSubTotal);
        preparedStatement.setDouble(7, invoiceTax);
        preparedStatement.setDouble(8, invoiceTotalAmount);
        preparedStatement.setString(9, invoiceStatus);
        preparedStatement.setDate(10, invoiceDueDate);

        preparedStatement.executeUpdate();
    }

    public void deleteInvoice(Integer invoiceID) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "DELETE FROM invoices WHERE invoiceID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, invoiceID);

        if (preparedStatement.executeUpdate() == 0) {
            throw new SQLException("Could not delete. Invoice with id " + invoiceID + " not found");
        }
    }

    public void editInvoiceStatus(String invoiceStatus, Integer invoiceID) throws SQLException {
        connection = DBConnectionManager.getConnection();

        String query = "UPDATE invoices SET invoiceStatus = ? WHERE invoiceID = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, invoiceStatus);
        preparedStatement.setInt(2, invoiceID);

        preparedStatement.executeUpdate();
    }

    public ObservableList<Invoice> addInvoiceToList() throws SQLException {
        connection = DBConnectionManager.getConnection();
        observableList = FXCollections.observableArrayList();

        String query ="SELECT invoiceID, invoiceNo, invoiceTitle, invoiceCompany, invoiceIssueDate, invoiceDescription, invoiceSubTotal, " +
                    " invoiceTax, invoiceTotalAmount, invoiceStatus, invoiceDueDate FROM invoices";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Invoice invoice = new Invoice();
            invoice.setInvoiceID(resultSet.getInt("invoiceID"));
            invoice.setInvoiceNo(resultSet.getString("invoiceNo"));
            invoice.setInvoiceTitle(resultSet.getString("invoiceTitle"));
            invoice.setInvoiceCompany(resultSet.getString("invoiceCompany"));
            invoice.setInvoiceIssueDate(resultSet.getDate("invoiceIssueDate"));
            invoice.setInvoiceDescription(resultSet.getString("invoiceDescription"));
            invoice.setInvoiceSubTotal(resultSet.getDouble("invoiceSubTotal"));
            invoice.setInvoiceTax(resultSet.getDouble("invoiceTax"));
            invoice.setInvoiceTotalAmount(resultSet.getDouble("invoiceTotalAmount"));
            invoice.setInvoiceStatus(resultSet.getString("invoiceStatus"));
            invoice.setInvoiceDueDate(resultSet.getDate("invoiceDueDate"));
            observableList.add(invoice);
        }
        return observableList;
    }
}
