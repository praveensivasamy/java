package com.praveen.batch.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.util.ExecutionBarrier;

/**
 * Executes {@link Pipeline}s either in serial or parallel depending on the threadsize
 * 
 * @author Praveen Sivasamy
 *
 */
public class PipelineExecutor {

    private static final Logger log = LoggerFactory.getLogger(PipelineExecutor.class);

    private AppConfiguration appConfig;
    private ExecutionBarrier barrier;
    private ExecutorService executor;
    /** Custom implementation ThreadFactory to ensure the pipelines are executed by same threads who created them */
    private PipelineThreadFactory pipelineThreadFactory;
    private List<Pipeline> pipelines;
    private int threads = 0;

    public PipelineExecutor(AppConfiguration config) {
        pipelineThreadFactory = new PipelineThreadFactory();
        this.appConfig = config;
        this.threads = appConfig.getThreads();
    }

    public void execute() {

        if (threads == 1) {
            processSerially();
        } else {
            processParallel();
        }
    }

    private void processSerially() {
        Pipeline pipeline = Pipeline.create(appConfig);
        log.info(pipeline.toString());
        pipeline.initialise();
        pipeline.processContracts();
        pipeline.tearDown();
    }

    private void processParallel() {
        executor = Executors.newFixedThreadPool(threads, pipelineThreadFactory);
        barrier = new ExecutionBarrier(threads + 1);//threads+1 to invite main thread as well for waitng party

        try {
            createPipelines();
            
            initialisePipelines();
            
            executePipelines();

            tearDownPipelines();

        } finally {
            executor.shutdown();
        }
    }

    private void createPipelines() {
        //Use overloaded methods to create ranges as needed
        List<PipelineRange> ranges = PipelineRange.createRange(threads);
        pipelines = new ArrayList<>();
        for (PipelineRange pipelineRange : ranges) {
            Pipeline pipeline = Pipeline.create(appConfig);
            pipeline.setPipeLineRange(pipelineRange);
            pipeline.setExecutionBarrier(barrier);
            pipelines.add(pipeline);
        }
    }

    private void initialisePipelines() {
        log.info("Initialise Pipelines");
        for (Pipeline pipeline : pipelines) {
            executor.execute(() ->
                {
                    log.info("Initialising pipeline {} in the thread {}", pipeline.getId(), Thread.currentThread().getName());
                    pipelineThreadFactory.put(Thread.currentThread(), pipeline);
                    pipeline.initialise();

                });
        }
        
        barrier.await("After Pipeline Initialisation");
    }

    private void executePipelines() {
        barrier.reset();
        for (int i = 0; i < threads; i++) {
            executor.execute(() ->
                {
                    Pipeline pipeline = pipelineThreadFactory.getPipeline(Thread.currentThread());
                    log.info("Executing pipeline {} in the thread {}", pipeline.getId(), Thread.currentThread().getName());
                    pipeline.processContracts();
                });
        }
        barrier.await("Executing pipelines");
    }

    private void tearDownPipelines() {
        log.info("tearDown Pipelines");

        barrier.reset();
        for (int i = 0; i < threads; i++) {
            executor.execute(() ->
                {
                    Pipeline pipeline = pipelineThreadFactory.getPipeline(Thread.currentThread());
                    log.info("tearDown pipeline {} in the thread {}", pipeline.getId(), Thread.currentThread().getName());
                    pipeline.tearDown();
                });
        }

        barrier.await("tearDown pipelines");
    }

}
