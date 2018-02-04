package com.praveen.batch.pipeline.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PipelineReader extends Reader {

	private static final Logger log = LoggerFactory.getLogger(PipelineReader.class);

	@Override
	public void initialise() {
		super.initialise();
		log.info("Initialise");
	}

	@Override
	public void process() {
		log.info("process");
	}

}
