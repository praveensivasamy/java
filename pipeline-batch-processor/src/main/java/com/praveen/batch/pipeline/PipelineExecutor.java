package com.praveen.batch.pipeline;

import com.praveen.batch.config.AppConfiguration;

public class PipelineExecutor {

	private AppConfiguration appConfig;

	public PipelineExecutor(AppConfiguration config) {

		this.appConfig = config;

	}

	public void execute() {

		int threads = 0;
		if (threads == 1) {
			processSerially();
		} else {
			processParallel(threads);
		}

	}

	private void processParallel(int threads) {

	}

	private void processSerially() {

	}

}
