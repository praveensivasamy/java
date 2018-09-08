package com.praveen.batch.pipeline.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleDBReader extends PipelineReader {

	private static final Logger log = LoggerFactory.getLogger(OracleDBReader.class);

	@Override
	public void initialize() {
		log.info("Initialise {}", this);
	}
}
