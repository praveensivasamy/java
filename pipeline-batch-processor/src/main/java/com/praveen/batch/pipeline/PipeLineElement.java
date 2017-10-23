package com.praveen.batch.pipeline;

public abstract class PipeLineElement {

	public void initialise() {

	}

	public abstract void process();

	public void tearDown() {

	}

}
