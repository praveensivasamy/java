package com.praveen.batch.pipeline;

import java.util.ArrayList;
import java.util.List;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.pipeline.process.Processor;
import com.praveen.batch.pipeline.reader.Reader;
import com.praveen.batch.pipeline.writer.Writer;

public class Pipeline {

	private static ThreadLocal<Pipeline> instances = new ThreadLocal<>();

	private List<Reader> readers = new ArrayList<>();
	private List<Processor> processors = new ArrayList<>();
	private List<Writer> writers = new ArrayList<>();

	public static Pipeline create(AppConfiguration appConfig) {
		return new Pipeline(appConfig.getPipelineReaders(), appConfig.getPipelineProcessors(), appConfig.getPipelineWriters());
	}

	public Pipeline(List<Reader> readers, List<Processor> processors, List<Writer> writers) {
		this.readers = readers;
		this.processors = processors;
		this.writers = writers;
	}

	private Pipeline getInstance() {
		return instances.get();
	}

	private void initialise() {

	}

	private void read() {

	}

	private void process() {

	}

	private void write() {

	}

}
