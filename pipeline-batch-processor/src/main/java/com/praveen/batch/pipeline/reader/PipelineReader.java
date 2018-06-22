package com.praveen.batch.pipeline.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.container.PipeLineDataContainer;

public class PipelineReader extends Reader {

    private static final Logger log = LoggerFactory.getLogger(PipelineReader.class);

    @Override
    public void initialize() {
        super.initialize();
        log.info("Initialise {}", this);
    }

    @Override
    public PipeLineDataContainer process(PipeLineDataContainer container) {
        container = new PipeLineDataContainer();
        return container;
    }

    @Override
    public String toString() {
        String res = getClass().getSimpleName();
        return res + "#" + hashCode();
    }

}
