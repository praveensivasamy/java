package com.praveen.batch.pipeline;

import java.util.ArrayList;
import java.util.List;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.pipeline.core.ExecutionBarrier;
import com.praveen.batch.pipeline.process.Processor;
import com.praveen.batch.pipeline.reader.Reader;
import com.praveen.batch.pipeline.writer.Writer;

public class Pipeline {

    private ThreadLocal<Pipeline> instances = new ThreadLocal<>();

    private List<Reader> readers = new ArrayList<>();
    private List<Processor> processors = new ArrayList<>();
    private List<Writer> writers = new ArrayList<>();
    private List<PipeLineElement> allElements = new ArrayList();

    private ExecutionBarrier barrier = null;

    public static Pipeline create(AppConfiguration appConfig) {
        return new Pipeline(appConfig.getPipelineReaders(), appConfig.getPipelineProcessors(), appConfig.getPipelineWriters());
    }

    public Pipeline(List<Reader> readers, List<Processor> processors, List<Writer> writers) {
        this.readers = readers;
        this.processors = processors;
        this.writers = writers;
        allElements.addAll(readers);
        allElements.addAll(processors);
        allElements.addAll(writers);
    }

    protected void initialise() {
        instances.set(this);
        allElements.forEach(element -> element.initialise());
        await("Pipeline Initialisation");
    }

    private void await(String wheretoWait) {

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "#" + hashCode();
    }

}
