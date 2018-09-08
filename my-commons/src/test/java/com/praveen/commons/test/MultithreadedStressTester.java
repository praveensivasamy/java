package com.praveen.commons.test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * A class that "bombards" an object by calling it many times, from multiple
 * threads. Used for stress-testing synchronisation.
 *
 * @author nat // @version 1.0
 * @author Praveen Sivasamy //@version 2.0
 * @version 2.0
 * @see <a href=
 *      "https://github.com/npryce/goos-code-examples/blob/master/testing-multithreaded-code/src/book/example/threading/races/">Gihub
 *      Example - testing-multithreaded-code</a>
 */
public class MultithreadedStressTester {
	/**
	 * The default number of threads to run concurrently.
	 */
	public static final int DEFAULT_THREAD_COUNT = 2;

	private final ExecutorService executor;
	private final int threadCount;
	private final int iterationCount;

	public MultithreadedStressTester(int iterationCount) {
		this(DEFAULT_THREAD_COUNT, iterationCount);
	}

	public MultithreadedStressTester(int threadCount, int iterationCount) {
		this.threadCount = threadCount;
		this.iterationCount = iterationCount;
		this.executor = Executors.newCachedThreadPool();
	}

	public MultithreadedStressTester(int threadCount, int iterationCount, ThreadFactory threadFactory) {
		this.threadCount = threadCount;
		this.iterationCount = iterationCount;
		this.executor = Executors.newCachedThreadPool(threadFactory);
	}

	public int totalActionCount() {
		return threadCount * iterationCount;
	}

	public void stress(final Runnable action) throws InterruptedException {
		spawnThreads(action).await();
	}

	public void bombardment(long timeoutMs, final Runnable action) throws InterruptedException, TimeoutException {
		if (!spawnThreads(action).await(timeoutMs, MILLISECONDS)) {
			throw new TimeoutException("timed out waiting for bombardment actions to complete successfully");
		}
	}

	private CountDownLatch spawnThreads(final Runnable action) {
		final CountDownLatch finished = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executor.execute(() -> {
				try {
					repeat(action);
				} finally {
					finished.countDown();
				}
			});
		}

		return finished;
	}

	private void repeat(Runnable action) {
		IntStream.rangeClosed(1, iterationCount).forEach(i -> {
			action.run();
		});
	}

	public void shutdown() {
		executor.shutdown();
	}
}