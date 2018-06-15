package com.praveen.batch.pipeline.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.container.DataContainer;

public class PipelineWriter extends Writer {

    private static final Logger log = LoggerFactory.getLogger(PipelineWriter.class);

    @Override
    public void initialise() {
        super.initialise();
        log.info("Initialise Writer");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
