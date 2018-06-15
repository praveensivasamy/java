package com.praveen.batch.pipeline.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.container.DataContainer;

public class PipelineProcessor extends Processor {

    private static final Logger log = LoggerFactory.getLogger(PipelineProcessor.class);

    @Override
    public void initialise() {
        log.info("Pipeline Processor");
    }

    @Override
    public DataContainer process(DataContainer container) {
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().concat("#") + hashCode();
    }

}
