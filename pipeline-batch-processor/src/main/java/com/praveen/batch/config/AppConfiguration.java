package com.praveen.batch.config;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.process.PipelineProcessor;
import com.praveen.batch.pipeline.process.Processor;
import com.praveen.batch.pipeline.reader.PipelineReader;
import com.praveen.batch.pipeline.reader.Reader;
import com.praveen.batch.pipeline.writer.PipelineWriter;
import com.praveen.batch.pipeline.writer.Writer;

public class AppConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AppConfiguration.class);

    /** The default instance */
    protected static AppConfiguration instance;

    private int threads = 1;

    public static AppConfiguration getConfiguration() {
        return instance;
    }

    public static AppConfiguration create() {
        AppConfiguration res = getConfiguration();
        if (res == null) {
            res = new AppConfiguration();
            instance = res;
            res.initialise();
        }
        return instance;

    }

    private void initialise() {
        log.info("Initialise config...");
        setUpThreadSize();
    }

    private void setUpThreadSize() {
        log.info("Setup Thread size");
        threads = 1;
    }

    public int getThreads() {
        return threads;
    }

    public List<Reader> getPipelineReaders() {
        return Arrays.asList(new PipelineReader());
    }

    public List<Processor> getPipelineProcessors() {
        return Arrays.asList(new PipelineProcessor());

    }

    public List<Writer> getPipelineWriters() {
        return Arrays.asList(new PipelineWriter());
    }

}
