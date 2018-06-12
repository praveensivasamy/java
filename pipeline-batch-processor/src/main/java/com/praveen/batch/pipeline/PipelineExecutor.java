package com.praveen.batch.pipeline;

import com.praveen.batch.config.AppConfiguration;

public class PipelineExecutor {

    private AppConfiguration appConfig;

    public PipelineExecutor(AppConfiguration config) {
        this.appConfig = config;
    }

    public void execute() {

        if (appConfig.getThreads() == 1) {
            processSerially();
        } else {
            processParallel(appConfig.getThreads());
        }
    }

    private void processParallel(int threads) {
        //Create Pipelines
    }

    private void processSerially() {

        Pipeline pipeline = Pipeline.create(appConfig);
        pipeline.initialise();

    }

}
