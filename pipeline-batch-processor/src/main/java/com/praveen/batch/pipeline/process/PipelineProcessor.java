package com.praveen.batch.pipeline.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.container.PipeLineDataContainer;

public class PipelineProcessor extends Processor {

	private static final Logger log = LoggerFactory.getLogger(PipelineProcessor.class);

	@Override
	public void initialize() {
		log.info("Pipeline Processor");
	}

	@Override
	public PipeLineDataContainer process(PipeLineDataContainer container) {
		return null;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName().concat("#") + hashCode();
	}

}
