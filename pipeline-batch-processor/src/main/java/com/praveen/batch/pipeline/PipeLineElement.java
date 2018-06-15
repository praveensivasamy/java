package com.praveen.batch.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.container.DataContainer;
import com.praveen.batch.report.AppStatistics;

public abstract class PipeLineElement {
    private static final Logger log = LoggerFactory.getLogger(PipeLineElement.class);

    protected AppStatistics stat;

    public void initialise() {
        stat = AppStatistics.getInstance();
    }

    public abstract DataContainer process(DataContainer container);

    public void tearDown() {

    }

}
