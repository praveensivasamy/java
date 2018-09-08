package com.praveen.batch.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.pipeline.container.PipeLineDataContainer;
import com.praveen.batch.pipeline.process.Processor;
import com.praveen.batch.pipeline.reader.Reader;
import com.praveen.batch.pipeline.writer.Writer;
import com.praveen.batch.util.ExecutionBarrier;
import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;

/**
 * <pre>
 * This is the base strucutre of the pipeline batch processor. It draws analogy
 * of a waterpipeline. The pipeline has pipeline elements.Each element has
 * respective functions. The following piprline elements are defined :
 * {@link Reader} : Reads the data from the input source -> Extractor
 * {@link Processor} : Works on the data received from the reader -> Transformer
 * {@link Writer} : Writes the data back to the system specified -> loader
 *
 * Holds {@link PipeLineElement} and operates on them
 *
 * @author Praveen Sivasamy
 *
 */

public class Pipeline {

	private static final Logger log = LoggerFactory.getLogger(Pipeline.class);

	/** Create a threadlocal of Pipeline element */
	private ThreadLocal<Pipeline> instances = new ThreadLocal<>();

	private List<Reader> readers = new ArrayList<>();
	private List<Processor> processors = new ArrayList<>();
	private List<Writer> writers = new ArrayList<>();
	private List<PipeLineElement> allPipelineElements = new ArrayList<>();

	private PipelineRange pipelineRange;
	private ExecutionBarrier barrier = null;
	private int pipelineId;

	private PipeLineDataContainer dataContainer;
	private static AtomicInteger counter = new AtomicInteger();

	public static Pipeline create(AppConfiguration appConfig) {
		return new Pipeline(appConfig.getPipelineReaders(), appConfig.getPipelineProcessors(),
				appConfig.getPipelineWriters());
	}

	public Pipeline(List<Reader> readers, List<Processor> processors, List<Writer> writers) {
		this.pipelineId = counter.incrementAndGet();
		this.readers = readers;
		this.processors = processors;
		this.writers = writers;
		allPipelineElements.addAll(readers);
		allPipelineElements.addAll(processors);
		allPipelineElements.addAll(writers);
	}

	public ThreadLocal<Pipeline> getInstance() {
		return instances;
	}

	protected void initialize() {
		instances.set(this);
		allPipelineElements.forEach(element -> {
			try {
				element.initialize();
			} catch (Exception e) {
				if (barrier != null) {
					barrier.setException(e);
					barrier.await("Exception in initialization");
				}
				throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e)
						.details("Error during pipeline initialization for : " + element);
			}
		});
		await("Pipeline Initialisation");
	}

	public void processContracts() {
		try {
			read();
			process();
			write();
		} finally {
			await("Pipeline processContracts");
		}
	}

	private void read() {

		for (Reader reader : readers) {
			dataContainer = reader.process(dataContainer);
		}

	}

	private void write() {
		log.info("Writing {}", this.getClass().getSimpleName());
	}

	private void process() {

	}

	public void tearDown() {
		await("Pipeline tearDown()");
	}

	private void await(String wheretoWait) {
		if (barrier != null) {
			barrier.await(wheretoWait);
			log.info("Pending parties {}", barrier.getPendingParties());
		}
	}

	public String getId() {
		return "Pipeline-" + pipelineId;
	}

	/**
	 * Boundaries for each pipeline
	 * 
	 * @param pipelineRange
	 */
	public void setPipeLineRange(PipelineRange pipelineRange) {
		this.pipelineRange = pipelineRange;
	}

	public void setExecutionBarrier(ExecutionBarrier barrier) {
		this.barrier = barrier;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Pipeline").append("\n");
		res.append("Readers    : " + readers).append(System.lineSeparator());
		res.append("Processors : " + processors).append(System.lineSeparator());
		res.append("Writers    : " + writers);
		return res.toString();
	}

}
