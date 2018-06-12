package com.praveen.batch.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.container.DataContainer;

public abstract class PipeLineElement {
    private static final Logger log = LoggerFactory.getLogger(PipeLineElement.class);

    public void initialise() {
        log.info("Initialise Pipeline element");
    }

    public abstract DataContainer process(DataContainer container);

    public void tearDown() {

    }

}
