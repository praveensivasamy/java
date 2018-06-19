package com.praveen.batch.report;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static void printStatistics() {

        log.info("Application Execution Time {}", getApplicationExecutionTime());

    }

    @Override
    public String toString() {
        return ToStringUtils.asString(this, "instanceName");
    }

}
