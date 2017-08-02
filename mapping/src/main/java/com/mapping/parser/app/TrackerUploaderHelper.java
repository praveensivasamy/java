package com.mapping.parser.app;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapping.parser.input.TrimatrixTracker;

public class TrackerUploaderHelper {

	private static final Logger log = LoggerFactory.getLogger(TrackerUploaderHelper.class);

	public static TrimatrixTracker getMergedTrimatrixReport(TrimatrixTracker sourceRecord, TrimatrixTracker destinationRecord) {

		if (sourceRecord == null) {
			return destinationRecord;
		}

		if (sourceRecord != null && destinationRecord == null) {
			return sourceRecord;
		}

		int res = new CompareToBuilder().append(sourceRecord.getInvoiceNumber(), destinationRecord.getInvoiceNumber())
				.append(sourceRecord.getOpenAmount(), destinationRecord.getOpenAmount())
				.toComparison();

		System.out.println(res);

		log.info("Source {}", sourceRecord.toString());
		log.info("Destination {}", destinationRecord.toString());

		if (res == 1) {

			log.info("Source {}", sourceRecord.toString());
			log.info("Destination {}", destinationRecord.toString());

		}
		return destinationRecord;
	}

}
