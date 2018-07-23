package com.praveen.batch.config;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.PipeLineElement;
import com.praveen.batch.pipeline.process.PipelineProcessor;
import com.praveen.batch.pipeline.process.Processor;
import com.praveen.batch.pipeline.reader.OracleDBReader;
import com.praveen.batch.pipeline.reader.PipelineReader;
import com.praveen.batch.pipeline.reader.Reader;
import com.praveen.batch.pipeline.writer.PipelineWriter;
import com.praveen.batch.pipeline.writer.Writer;
import com.praveen.batch.util.AppProperties;
import com.praveen.commons.exception.ApplicationException;

/**
 * Base configuration which is accessible across various {@link PipeLineElement}s
 * 
 * The pre-requisite is that this class has to be initialised before using.
 * 
 * @author Praveen Sivasamy
 */
public class AppConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AppConfiguration.class);

    /** The default instance. */
    protected static AppConfiguration instance;

    private int threads = 1;

    public static AppConfiguration getConfiguration() {
        if (instance == null) {
            throw ApplicationException
                    .warn("Incorrect API usage.Initialise " + AppConfiguration.class.getSimpleName() + " using initConfig().");
        }
        return instance;
    }

    public static void initConfig() {
        AppConfiguration res = new AppConfiguration();
        res.initialise();
        instance = res;
    }

    private void initialise() {
        log.info("Initialise config...");
        setUpThreading();
    }

    private void setUpThreading() {
        threads = AppProperties.instance().getThreads();
    }

    public int getThreads() {
        return this.threads;
    }

    /**
     * override default threadsize
     * 
     * @param threads
     */
    public void setThreadSize(int threads) {
        this.threads = threads;
    }

    public List<Reader> getPipelineReaders() {
        return Arrays.asList(new PipelineReader(), new OracleDBReader());
    }

    public List<Processor> getPipelineProcessors() {
        return Arrays.asList(new PipelineProcessor());
    }

    public List<Writer> getPipelineWriters() {
        return Arrays.asList(new PipelineWriter());
    }

}
