package com.praveen.batch.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PipeLineElement {

	private static final Logger log = LoggerFactory.getLogger(PipeLineElement.class);

	public void initialise() {
		log.info("Initialise called");
	}

	public abstract void process();

	public void tearDown() {

	}

}
