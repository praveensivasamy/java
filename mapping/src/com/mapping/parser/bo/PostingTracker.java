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
	
	
	
}
