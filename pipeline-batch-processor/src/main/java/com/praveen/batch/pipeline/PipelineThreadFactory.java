package com.praveen.batch.pipeline;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;

/**
 * <pre>
 * Serves two purposes :
 * 
 * 1.Assigns name to each pipeline thread of the format "Pipeline-Thread-(no)"
 * 
 * 2.Holds mapping of {@link Thread#currentThread()} and the {@link Pipeline}.This mapping ensures that, each pipeline is executed with its respecitve
 * thread with which it was initialised
 * 
 * The {@link #mapping} is filled during the {@link Pipeline#initialize()} and re-used in {@link Pipeline#processContracts()} 
 *  and {@link Pipeline#tearDown()}
 * 
 * @author Praveen Sivasamy
 *
 */
public class PipelineThreadFactory implements ThreadFactory {
    /** key : Thread ,  value : Pipeline correspoingng to the thread with which it was initialised  */
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