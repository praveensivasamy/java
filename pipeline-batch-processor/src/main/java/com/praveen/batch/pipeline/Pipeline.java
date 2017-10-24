package com.praveen.batch.pipeline;

import java.util.ArrayList;
import java.util.List;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.pipeline.process.PipelineProcessor;
import com.praveen.batch.pipeline.reader.PipelineReader;
import com.praveen.batch.pipeline.writer.PipelineWriter;

public class Pipeline {

	private static ThreadLocal<Pipeline> instances = new ThreadLocal<>();

	private List<PipelineReader> readers = new ArrayList<>();
	private List<PipelineProcessor> processors = new ArrayList<>();
	private List<PipelineWriter> writers = new ArrayList<>();

	public static Pipeline create(AppConfiguration appConfig) {
		return new Pipeline(appConfig.getPipelineReaders(), appConfig.getPipelineProcessors(), appConfig.getPipelineWriters());
	}

	public Pipeline(List<PipelineReader> readers, List<PipelineProcessor> processors, List<PipelineWriter> writers) {
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
