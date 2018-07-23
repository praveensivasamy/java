package com.praveen.batch.pipeline;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.util.AppProperties;
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

    private AtomicBoolean done = new AtomicBoolean(false);

    public PipelineExecutor(AppConfiguration config) {
        this.pipelineThreadFactory = new PipelineThreadFactory();
        this.appConfig = config;
        this.threads = appConfig.getThreads();
    }

    public void execute() {
        String stopFile = AppProperties.instance().getProperty("stopFile");
        if (stopFile != null) {
            addShutdownHook(stopFile);
        }
        if (threads == 1) {
            processSerially();
        } else {
            processParallel();
        }
        done.set(true);
    }

    private void addShutdownHook(String stopFile) {
        log.info("Will stop if {} is present", stopFile);
        new Thread(() ->
            {
                while (!done.get()) {
                    File file = new File(stopFile);
                    if (!file.exists()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                        }
                    } else {
                        log.info("Found the file {} and exiting", stopFile);
                        System.exit(99);
                    }
                }
            }, "PipelineStopper").start();
    }

    private void processSerially() {
        Pipeline pipeline = Pipeline.create(appConfig);
        log.info(pipeline.toString());
        pipeline.initialize();
        pipeline.processContracts();
        pipeline.tearDown();
    }

    private void processParallel() {
        executor = Executors.newFixedThreadPool(threads, pipelineThreadFactory);
        barrier = new ExecutionBarrier(threads + 1);//threads+1 to invite main thread as well for waitng party

        try {
            createPipelines();
            initializePipelines();
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

    private void initializePipelines() {
        log.info("Initialise Pipelines");
        for (Pipeline pipeline : pipelines) {
            executor.execute(() ->
                {
                    log.info("Initialising pipeline {} in the thread {}", pipeline.getId(), Thread.currentThread().getName());
                    pipelineThreadFactory.put(Thread.currentThread(), pipeline);
                    pipeline.initialize();

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
