package com.praveen.batch.pipeline.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.container.PipeLineDataContainer;

public class PipelineWriter extends Writer {

    private static final Logger log = LoggerFactory.getLogger(PipelineWriter.class);

    @Override
    public void initialise() {
        super.initialise();
        log.info("Initialise Writer {} ",this);
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
