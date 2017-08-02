CREATE TABLE PUBLIC.T_TRIMATRIX_REPORT (
	MAPPED_CUSTOMER VARCHAR(50),
	MAPPED_TYPE VARCHAR(25),
	CONSOLIDATED_BILLING_NUMBER VARCHAR(50),
	INVOICE_NUMBER VARCHAR(50) NOT NULL,
	RECEIPT_NUMBER BIGINT,
	INVOICE_DATE DATE,
	CURRENCY VARCHAR(5),
	OPEN_AMOUNT DECIMAL(25,4),
	OUTSTANDING_DAYS DECIMAL(3,0),
	AGING_BUCKET VARCHAR(25),
	WON DECIMAL(15,0),
	PROJECT_NAME VARCHAR(50),
	TEMPLATE_FILE VARCHAR(1000),
	UPLOADEDON TIMESTAMP,
	MODIFIEDON TIMESTAMP NOT NULL,
	CONSTRAINT T_TRIMATRIX_REPORT_PK PRIMARY KEY (INVOICE_NUMBER)
) ;
CREATE UNIQUE INDEX PRIMARY_KEY_9 ON PUBLIC.T_TRIMATRIX_REPORT (INVOICE_NUMBER) ;
CREATE INDEX T_TRIMATRIX_REPORT_INVOICE_NUMBER_IDX ON PUBLIC.T_TRIMATRIX_REPORT (INVOICE_NUMBER) ;
CREATE INDEX T_TRIMATRIX_REPORT_RECEIPT_NUMBER_IDX ON PUBLIC.T_TRIMATRIX_REPORT (RECEIPT_NUMBER) ;
