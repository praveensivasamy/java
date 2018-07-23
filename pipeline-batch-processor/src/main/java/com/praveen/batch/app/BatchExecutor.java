package com.praveen.batch.app;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.batch.config.AppConfiguration;
import com.praveen.batch.enums.AppIdentifier;
import com.praveen.batch.pipeline.PipelineExecutor;
import com.praveen.batch.report.AppStatistics;
import com.praveen.commons.hibernate.DBConnectionProvider;
import com.praveen.commons.hibernate.HibernateProvider;
import com.praveen.commons.hibernate.JpaDao;

/**
 * Base Application Invoker.
 * 
 * @author Praveen Sivasamy
 */

public class BatchExecutor {
    private static final Logger log = LoggerFactory.getLogger(BatchExecutor.class);

    private static final String MAPPING_CONFIG_FILE = "hibernate.batch.cfg.xml";

    private HibernateProvider configProvider;

    private JpaDao dao;

    public static void main(String... args) {
        AppStatistics.applicationStarted();
        new BatchExecutor().run(args);
    }

    public void run(String... args) {
        log.info("App run");
        initialize();
        process();
        tearDown();
        AppStatistics.applicationFinished();
        AppStatistics.printStatistics(AppIdentifier.BASE);
    }

    private void initialize() {
        log.info("Initialize");
        AppConfiguration.initConfig();
        initializeHibernate();
    }

    private void initializeHibernate() {
        configProvider = HibernateProvider.instance(MAPPING_CONFIG_FILE, null);
        dao = JpaDao.instance(configProvider);
        log.info("{}", dao.toString());
    }

    private void process() {
        log.info("Process");
        testDBConnection();
        AppConfiguration config = AppConfiguration.getConfiguration();
        PipelineExecutor executor = new PipelineExecutor(config);
        executor.execute();
    }

    private void testDBConnection() {
        try {
            Connection con;
            for (int i = 0; i < 1000; i++) {
                con = DBConnectionProvider.instance().getConnection();
                log.info("Connection {}", con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tearDown() {
        log.info("Tear Down");
        DBConnectionProvider.logStat();
        HibernateProvider.tearDownAll();
    }
}
