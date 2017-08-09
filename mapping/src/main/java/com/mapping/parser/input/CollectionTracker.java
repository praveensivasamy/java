package com.mapping.parser.input;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import com.mapping.enums.BilledCurrency;
import com.praveen.commons.utils.ToStringUtils;

@Entity(name = "T_COLLECTION_REPORT")
@Table(name = "T_COLLECTION_REPORT")
public class CollectionTracker implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Id
	@Column(name = "RECEIPT_NUMBER")
	private long receiptNumber;

	@Column(name = "RECEIPT_DATE")
	@Temporal(TemporalType.DATE)
	private Date receiptDate;

	@Column(name = "RECEIPT_CURRENCY")
	@Enumerated(EnumType.STRING)
	private BilledCurrency currency;

	@Column(name = "ALLOCATED_AMOUNT")
	private double allocatedAmount;

	@Column(name = "RECEIVED_AMOUNT")
	private double receivedAmount;

	@Column(name = "APPLIED_DATE")
	@Temporal(TemporalType.DATE)
	private Date dateApplied;

	@Id
	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;

	@Column(name = "INVOICE_DATE")
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;

	@Column(name = "WON")
	private int won;

	@Column(name = "INVOICE_CURRENCY")
	@Enumerated(EnumType.STRING)
	private BilledCurrency invoiceCurrency;

	@Column(name = "APPLIED_INVOICE_AMOUNT")
	private double adjustedInvoiceAmount;

	@Column(name = "APPLIED_RECEIPT_AMOUNT")
	private double allocatedReceiptAmount;

	@Column(name = "UNAPPLIED_RECEIPT_AMOUNT")
	private double unappliedReceiptAMount;

	@Column(name = "CONTRACT_ID")
	private String contractId;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "UPLOADEDON", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date uploadTime;

	@Column(name = "TEMPLATE_FILE")
	private String uploadedFile;

	@Override
	public String toString() {

		return ToStringUtils.asString(this, "customerName", "receiptNumber", "invoiceNumber");
	}

	public CollectionTracker reset() {
		this.invoiceNumber = null;
		this.receiptNumber = 0;
		return this;
	}

	/*@Override
	public boolean equals(Object other) {

		if (this == other) {
			return true;
		}

		if (other == null) {
			return false;
		}

		if (!(other instanceof CollectionTracker)) {
			return false;
		}

		CollectionTracker castOther = (CollectionTracker) other;

		return (getReceiptNumber() == castOther.getReceiptNumber()) &&
				((getInvoiceNumber() != null) && (castOther.getInvoiceNumber() != null)) &&
				(getInvoiceNumber().equalsIgnoreCase(castOther.getInvoiceNumber()));

	}

	@Override
	public int hashCode() {
		int result = 17;
		result = (37 * result) + ((int) getReceiptNumber());
		return (37 * result) + (getInvoiceNumber() == null ? 0 : getInvoiceNumber().hashCode());

	}*/

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(long receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public BilledCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(BilledCurrency currency) {
		this.currency = currency;
	}

	public double getAllocatedAmount() {
		return allocatedAmount;
	}

	public void setAllocatedAmount(double allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}

	public double getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public int getWon() {
		return won;
	}

	public void setWon(int won) {
		this.won = won;
	}

	public BilledCurrency getInvoiceCurrency() {
		return invoiceCurrency;
	}

	public void setInvoiceCurrency(BilledCurrency invoiceCurrency) {
		this.invoiceCurrency = invoiceCurrency;
	}

	public double getAdjustedInvoiceAmount() {
		return adjustedInvoiceAmount;
	}

	public void setAdjustedInvoiceAmount(double adjustedInvoiceAmount) {
		this.adjustedInvoiceAmount = adjustedInvoiceAmount;
	}

	public double getAllocatedReceiptAmount() {
		return allocatedReceiptAmount;
	}

	public void setAllocatedReceiptAmount(double allocatedReceiptAmount) {
		this.allocatedReceiptAmount = allocatedReceiptAmount;
	}

	public double getUnappliedReceiptAMount() {
		return unappliedReceiptAMount;
	}

	public void setUnappliedReceiptAMount(double unappliedReceiptAMount) {
		this.unappliedReceiptAMount = unappliedReceiptAMount;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(String uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

}
