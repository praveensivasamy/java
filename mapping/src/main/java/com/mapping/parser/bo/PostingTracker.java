/**
 * 
 */
package com.mapping.parser.bo;

import java.io.Serializable;
import java.util.Date;

import com.mapping.enums.Status;

public class PostingTracker implements Serializable {

    private static final long serialVersionUID = 1L;
    private String projectName;
    private String lebIdentifier;
    private String ultimatixInvoice;
    private String manualInvoice;
    private Date invoiceDate;
    private String invoiceDescription;
    private int invoiceAmountTaxed;
    private int invoiceAmount;
    private Date postingDate;
    private Status paymentStatus;
    private Date paymentDate;
    private String paymentAmount;
    private String receiptNumber;
    private Status closureStatus;
    private Date closedDate;
    private Status reportedB2C;
    private Status reportTracked;

    public String getProjectName() {
	return projectName;
    }

    public void setProjectName(String projectName) {
	this.projectName = projectName;
    }

    public String getLebIdentifier() {
	return lebIdentifier;
    }

    public void setLebIdentifier(String lebIdentifier) {
	this.lebIdentifier = lebIdentifier;
    }

    public String getUltimatixInvoice() {
	return ultimatixInvoice;
    }

    public void setUltimatixInvoice(String ultimatixInvoice) {
	this.ultimatixInvoice = ultimatixInvoice;
    }

    public String getManualInvoice() {
	return manualInvoice;
    }

    public void setManualInvoice(String manualInvoice) {
	this.manualInvoice = manualInvoice;
    }

    public Date getInvoiceDate() {
	return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
	this.invoiceDate = invoiceDate;
    }

    public String getInvoiceDescription() {
	return invoiceDescription;
    }

    public void setInvoiceDescription(String invoiceDescription) {
	this.invoiceDescription = invoiceDescription;
    }

    public int getInvoiceAmountTaxed() {
	return invoiceAmountTaxed;
    }

    public void setInvoiceAmountTaxed(int invoiceAmountTaxed) {
	this.invoiceAmountTaxed = invoiceAmountTaxed;
    }

    public int getInvoiceAmount() {
	return invoiceAmount;
    }

    public void setInvoiceAmount(int invoiceAmount) {
	this.invoiceAmount = invoiceAmount;
    }

    public Date getPostingDate() {
	return postingDate;
    }

    public void setPostingDate(Date postingDate) {
	this.postingDate = postingDate;
    }

    public Status getPaymentStatus() {
	return paymentStatus;
    }

    public void setPaymentStatus(Status paymentStatus) {
	this.paymentStatus = paymentStatus;
    }

    public Date getPaymentDate() {
	return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
	this.paymentDate = paymentDate;
    }

    public String getPaymentAmount() {
	return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
	this.paymentAmount = paymentAmount;
    }

    public String getReceiptNumber() {
	return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
	this.receiptNumber = receiptNumber;
    }

    public Status getClosureStatus() {
	return closureStatus;
    }

    public void setClosureStatus(Status closureStatus) {
	this.closureStatus = closureStatus;
    }

    public Date getClosedDate() {
	return closedDate;
    }

    public void setClosedDate(Date closedDate) {
	this.closedDate = closedDate;
    }

    public Status getReportedB2C() {
	return reportedB2C;
    }

    public void setReportedB2C(Status reportedB2C) {
	this.reportedB2C = reportedB2C;
    }

    public Status getReportTracked() {
	return reportTracked;
    }

    public void setReportTracked(Status reportTracked) {
	this.reportTracked = reportTracked;
    }

}
