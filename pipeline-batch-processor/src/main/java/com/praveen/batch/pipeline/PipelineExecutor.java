package com.praveen.batch.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.pipeline.core.ExecutionBarrier;

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
    private PipelineThreadFactory pipelineThreadFactory;
    private List<Pipeline> pipelines;

    public PipelineExecutor(AppConfiguration config) {
        pipelineThreadFactory = new PipelineThreadFactory();
        this.appConfig = config;
    }

    public void execute() {
        
        if (appConfig.getThreads() == 1) {
            processSerially();
        } else {
            processParallel(appConfig.getThreads());
        }
    }

    private void processSerially() {
        Pipeline pipeline = Pipeline.create(appConfig);
        log.info(pipeline.toString());
        pipeline.initialise();
        pipeline.processContracts();
        pipeline.tearDown();
    }

    private void processParallel(int threads) {

        executor = Executors.newFixedThreadPool(threads, pipelineThreadFactory);
        barrier = new ExecutionBarrier(threads + 1);
        try {
            createPipelines(threads);
            initialisePipelines();
            executePipelines();
            tearDownPipelines();
        } finally {
            executor.shutdown();
        }

    }

    private void createPipelines(int threads) {
        //Use overloaded methods to create ranges
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
        barrier.await("After PipelineInitialisation");
    }

    private void executePipelines() {
        log.info("execute Pipelines");
        barrier.reset();
    }

    private void tearDownPipelines() {
        log.info("tearDown Pipelines");
        barrier.reset();
    }

}
