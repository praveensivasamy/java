package com.praveen.batch.pipeline.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.container.DataContainer;

public class PipelineReader extends Reader {

    private static final Logger log = LoggerFactory.getLogger(PipelineReader.class);

    @Override
    public void initialise() {
        super.initialise();
        log.info("Pipeline Reader");
    }

    @Override
    public DataContainer process(DataContainer container) {
        return null;
    }

}
