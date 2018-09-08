package com.praveen.batch.report;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.enums.AppIdentifier;
import com.praveen.batch.enums.AppStatistic;
import com.praveen.commons.utils.ToStringUtils;

/**
 * Application statistics for reports
 *
 * @author Praveen Sivasamy
 *
 */
public class AppStatistics {

	private static final Logger log = LoggerFactory.getLogger(AppStatistics.class);

	private String instanceName;

	/**
	 * statistic container on instance level <=> thread
	 */
	private static Map<AppStatistic, AtomicLong> statisticsContainer = new LinkedHashMap<>();

	private static ThreadLocal<AppStatistics> instance = new ThreadLocal<AppStatistics>() {
		@Override
		protected AppStatistics initialValue() {
			AppStatistics stat = new AppStatistics();
			stat.instanceName = Thread.currentThread().getName();
			return stat;
		};
	};

	private AppStatistics() {

	}

	static {

		for (AppStatistic statistic : AppStatistic.values()) {
			statisticsContainer.put(statistic, new AtomicLong(0));
		}

	}

	public static AppStatistics getInstance() {
		return instance.get();
	}

	private static StopWatch appStopWatch = new StopWatch();

	private StopWatch pipelineInitialisation;

	public static void applicationStarted() {
		appStopWatch.start();
	}

	public static void applicationFinished() {
		appStopWatch.stop();
	}

	private static long getApplicationExecutionTime() {
		return appStopWatch.getTime();
	}

	public static void updateStat(AppStatistic statistic, int delta) {
		statisticsContainer.get(statistic).addAndGet(delta);
	}

	public static void printStatistics(AppIdentifier appId) {
		log.info(MarkerFactory.getMarker("MONITOR"), output(appId));
	}

	private static void append(StringBuilder s, String string, int indentLevel) {
		String indent = "     ";
		s.append(repeat(indent, indentLevel)).append(" - ").append(string).append("\n");
	}

	private static String repeat(String string, int indentLevel) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < indentLevel; i++) {
			res.append(string);
		}
		return res.toString();
	}

	private static String align(int tab, String label) {
		label = label.trim();
		return String.format("%-" + tab + "s: ", label);
	}

	private static String output(AppIdentifier appId) {
		StringBuilder s = new StringBuilder(AppConfiguration.getConfiguration().toString() + "\n");
		s.append("Performance Statistics \n");
		append(s, align(50, "Total application time                ") + getApplicationExecutionTime(), 0);
		return s.toString();
	}

	@Override
	public String toString() {
		return ToStringUtils.asString(this, "instanceName");
	}

}
