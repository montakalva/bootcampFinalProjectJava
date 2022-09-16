package com.example.housemanagementsystem.invoices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Invoice {
    private Integer invoiceID;
    private String invoiceNo;
    private String invoiceTitle;
    private String invoiceCompany;
    private String invoiceIssueDate;
    private String invoiceDescription;
    private double invoiceSubTotal;
    private double invoiceTax;
    private double invoiceTotalAmount;
    private String invoiceStatus;
    private String invoicePaidOn;
}