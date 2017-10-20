package com.praveen.batch.config;

public class AppConfiguration {

	/** For parallel runs support */
	protected static ThreadLocal<AppConfiguration> instances = new ThreadLocal<>();

	/** The default instance */
	protected static AppConfiguration instance;

	public AppConfiguration() {
	}

	public static AppConfiguration getConfiguration() {
		AppConfiguration res = instances.get();
		if (res != null) {
			return res;
		}
		return instance;
	}

}
