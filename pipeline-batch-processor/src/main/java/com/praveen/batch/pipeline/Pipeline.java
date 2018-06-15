package com.praveen.batch.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.pipeline.core.ExecutionBarrier;
import com.praveen.batch.pipeline.process.Processor;
import com.praveen.batch.pipeline.reader.Reader;
import com.praveen.batch.pipeline.writer.Writer;
import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;

/**
 * Holds {@link PipeLineElement} and operates on them
 * 
 * @author Praveen Sivasamy
 *
 */

public class Pipeline {

    /** Create a threadlocal of Pipeline element */
    private ThreadLocal<Pipeline> instances = new ThreadLocal<>();

    private List<Reader> readers = new ArrayList<>();
    private List<Processor> processors = new ArrayList<>();
    private List<Writer> writers = new ArrayList<>();
    private List<PipeLineElement> allPipelineElements = new ArrayList<>();

    private PipelineRange pipelineRange;
    private ExecutionBarrier barrier = null;
    private int pipelineId;
    private static AtomicInteger counter = new AtomicInteger();

    public static Pipeline create(AppConfiguration appConfig) {
        return new Pipeline(appConfig.getPipelineReaders(), appConfig.getPipelineProcessors(), appConfig.getPipelineWriters());
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

    protected void initialise() {
        instances.set(this);
        allPipelineElements.forEach(element ->
            {
                try {
                    element.initialise();
                } catch (Exception e) {
                    if (barrier != null) {
                        barrier.setException(e);
                        barrier.await("Exception in initialization");
                    }
                    throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e).details("Error during pipeline initialization for : " + element);
                }
            });

        await("Pipeline Initialisation");
    }

    public void processContracts() {
        read();
        process();
        write();
    }

    private void read() {

    }

    private void write() {
    }

    private void process() {

    }

    public void tearDown() {

    }

    private void await(String wheretoWait) {
        if (barrier != null) {
            barrier.await(wheretoWait);
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
