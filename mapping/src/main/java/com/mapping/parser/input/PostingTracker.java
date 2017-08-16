/**
 *
 */
package com.mapping.parser.input;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mapping.enums.Status;
import com.praveen.commons.utils.ToStringUtils;

@Entity
@Table(name = "")
public class PostingTracker extends AbstractMappingEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "")
	private String projectName;
	@Column(name = "")
	private String LEBIdentifier;
	@Id
	@Column(name = "")
	private String ultimatixInvoice;
	@Id
	@Column(name = "")
	private String manualInvoice;
	@Column(name = "")
	private Date invoiceDate;
	@Column(name = "")
	private String invoiceDescription;
	@Column(name = "")
	private int invoiceAmountTaxed;
	@Column(name = "")
	private int invoiceAmount;
	@Column(name = "")
	private Date postingDate;
	@Column(name = "")
	private Status paymentStatus;
	@Column(name = "")
	private Date paymentDate;
	@Column(name = "")
	private String paymentAmount;
	@Column(name = "")
	private String receiptNumber;
	@Column(name = "")
	private Status closureStatus;
	@Column(name = "")
	private Date closedDate;
	@Column(name = "")
	private Status reportedB2C;
	@Column(name = "")
	private Status reportTracked;

	@Override
	public boolean isSave() {
		return ultimatixInvoice != null;
	}

	@Override
	public String toString() {
		return ToStringUtils.asString(this, "ultimatixInvoice", "manualInvoice");
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLEBIdentifier() {
		return LEBIdentifier;
	}

	public void setLEBIdentifier(String lEBIdentifier) {
		LEBIdentifier = lEBIdentifier;
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
