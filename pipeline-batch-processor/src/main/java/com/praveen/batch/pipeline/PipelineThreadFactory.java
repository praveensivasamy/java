package com.praveen.batch.pipeline;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;

public class PipelineThreadFactory implements ThreadFactory {

	private Map<Thread, Pipeline> mapping = new ConcurrentHashMap<Thread, Pipeline>();
	private int index = 1;

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, "Pipeline-Thread-" + index++);
	}

	public void put(Thread thread, Pipeline pipeline) {
		mapping.put(thread, pipeline);
	}

	public Pipeline getPipeline(Thread thread) {
		return mapping.get(thread);
	}
}