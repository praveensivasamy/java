/**
 * 
 */
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

import com.mapping.enums.BilledCurrency;
import com.praveen.commons.utils.ToStringUtils;

/**
 * @author Praveen Sivasamy
 *
 */
@Entity
@Table(name = "T_TRIMATRIX_REPORT")
public class TrimatrixTracker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "MAPPED_CUSTOMER")
	private String mappedCustomer;

	@Column(name = "MAPPED_TYPE")
	private String rowType;

	@Column(name = "CONSOLIDATED_BILLING_NUMBER")
	private String consolidatedBillingNumber;

	@Id
	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;

	@Column(name = "RECEIPT_NUMBER")
	private long receiptNumber;

	@Column(name = "INVOICE_DATE")
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;

	@Column(name = "CURRENCY")
	@Enumerated(EnumType.STRING)
	private BilledCurrency currency;

	@Column(name = "OPEN_AMOUNT")
	private double openAmount;

	@Column(name = "OUTSTANDING_DAYS")
	private int outstandingDays;

	@Column(name = "AGING_BUCKET")
	private String agingBucket;

	@Column(name = "WON")
	private int won;

	@Column(name = "PROJECT_NAME")
	private String projectName;

	@Override
	public String toString() {
		return ToStringUtils.asString(this, "mappedCustomer", "rowType", "consolidatedBillingNumber", "invoiceNumber", "receiptNumber", "invoiceDate", "currency",
				"openAmount", "outstandingDays", "agingBucket", "won", "projectName");
	}

	public String getMappedCustomer() {
		return mappedCustomer;
	}

	public void setMappedCustomer(String mappedCustomer) {
		this.mappedCustomer = mappedCustomer;
	}

	public String getRowType() {
		return rowType;
	}

	public void setRowType(String rowType) {
		this.rowType = rowType;
	}

	public String getConsolidatedBillingNumber() {
		return consolidatedBillingNumber;
	}

	public void setConsolidatedBillingNumber(String consolidatedBillingNumber) {
		this.consolidatedBillingNumber = consolidatedBillingNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public double getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(long receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BilledCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(BilledCurrency currency) {
		this.currency = currency;
	}

	public double getOpenAmount() {
		return openAmount;
	}

	public void setOpenAmount(double openAmount) {
		this.openAmount = openAmount;
	}

	public int getOutstandingDays() {
		return outstandingDays;
	}

	public void setOutstandingDays(int outstandingDays) {
		this.outstandingDays = outstandingDays;
	}

	public String getAgingBucket() {
		return agingBucket;
	}

	public void setAgingBucket(String agingBucket) {
		this.agingBucket = agingBucket;
	}

	public int getWon() {
		return won;
	}

	public void setWon(int won) {
		this.won = won;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
