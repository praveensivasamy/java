package com.praveen.batch.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.container.PipeLineDataContainer;
import com.praveen.batch.report.AppStatistics;

/**
 * These are working units inside the pipeline
 *
 * @author Praveen Sivasamy
 *
 */

public abstract class PipeLineElement {

	private static final Logger log = LoggerFactory.getLogger(PipeLineElement.class);

	protected AppStatistics stat;

	public void initialize() {
		stat = AppStatistics.getInstance();
	}

	public abstract PipeLineDataContainer process(PipeLineDataContainer container);

	public void tearDown() {

	}

}
