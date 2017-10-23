package com.praveen.batch.config;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.process.PipelineProcessor;
import com.praveen.batch.pipeline.reader.PipelineReader;
import com.praveen.batch.pipeline.writer.PipelineWriter;

public class AppConfiguration {
	private static final Logger log = LoggerFactory.getLogger(AppConfiguration.class);

	/** The default instance */
	protected static AppConfiguration instance;

	public static AppConfiguration getConfiguration() {
		return instance;
	}

	public static AppConfiguration create() {
		AppConfiguration res = getConfiguration();
		if (res == null) {
			res = new AppConfiguration();
			instance = res;
			res.initialise();

		}
		return instance;

	}

	private void initialise() {
		log.info("Initialise config...");
		setUpThreadSize();
	}

	private static void setUpThreadSize() {
		log.info("Setup Thread size");
	}

	protected List<PipelineReader> getPipelineReaders() {
		return Arrays.asList(new PipelineReader());
	}

	protected List<PipelineProcessor> getPipelineProcessors() {
		return Arrays.asList(new PipelineProcessor());

	}

	protected List<PipelineWriter> getPipelineWriters() {
		return Arrays.asList(new PipelineWriter());

	}

}
