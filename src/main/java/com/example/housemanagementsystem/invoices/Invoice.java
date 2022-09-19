package com.example.housemanagementsystem.invoices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Invoice {
    private Integer invoiceID;
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
}