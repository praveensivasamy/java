package com.praveen.batch.test.infra;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.pipeline.PipelineThreadFactory;
import com.praveen.batch.report.AppStatistics;

class TestThreadSyncWithThreadLocal {

    private static final Logger log = LoggerFactory.getLogger(TestThreadSyncWithThreadLocal.class);

    @Test
    void test() {
        PipelineThreadFactory pipelineThreadFactory = new PipelineThreadFactory();
        int threads = 5;
        ExecutorService exec = Executors.newFixedThreadPool(threads, pipelineThreadFactory);
        try {
            for (int i = 0; i < threads; i++) {
                exec.execute(() ->
                    {
                        log.info("Executor" + AppStatistics.getInstance());
                    });
            }
        } finally {
            exec.shutdown();
        }
    }

}
