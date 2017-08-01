package com.mapping.parser.app;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.mapping.parser.input.TrimatrixTracker;

public class TrackerUploaderHelper {

	public static TrimatrixTracker getMergedTrimatrixReport(TrimatrixTracker sourceRecord, TrimatrixTracker destinationRecord) {

		int res = new CompareToBuilder().append(sourceRecord.getInvoiceNumber(), destinationRecord.getInvoiceNumber())
				.append(sourceRecord.getOpenAmount(), destinationRecord.getOpenAmount())
				.toComparison();

		System.out.println(res);
		if (res == 0) {
			return null;
		} else if (res == 1) {

		} else {
			return destinationRecord;
		}
		return destinationRecord;
	}

}
