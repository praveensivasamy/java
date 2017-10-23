package com.praveen.batch.app;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.pipeline.PipelineExecutor;

/**
 * Base Application Invoker
 * 
 * @author Praveen Sivasamy
 *
 */

public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String... args) {
		new App().run(args);
	}

	public void run(String... args) {
		log.info("App run");
		initialise();
		process();
		tearDown();
	}

	private void initialise() {
		log.info("Initialise");
		AppConfiguration config = AppConfiguration.create();
	}

	private void process() {
		log.info("Process");

		AppConfiguration config = AppConfiguration.getConfiguration();

		PipelineExecutor executor = new PipelineExecutor(config);
		executor.execute();
	}

	private void tearDown() {
		log.info("Tear Down");
	}

}
