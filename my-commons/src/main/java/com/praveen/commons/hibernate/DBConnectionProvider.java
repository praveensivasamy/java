package com.praveen.commons.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide one Connection per thread.
 * <p>
 * Creation of connection object is done by the Hibernate base class {@link DriverManagerConnectionProviderImpl}
 *
 * @author Praveen
 *
 */
public class DBConnectionProvider extends DriverManagerConnectionProviderImpl {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DBConnectionProvider.class);

    private static AtomicInteger totalRequests = new AtomicInteger(0);
    private static AtomicInteger requestsServedFromCache = new AtomicInteger(0);
    private static AtomicInteger freshConnection = new AtomicInteger(0);

    private ThreadLocal<Connection> connections = new ThreadLocal<>();

    private static DBConnectionProvider instance;

    /**
     * Called during hibernate config initilaization
     * 
     * @see hibernate.cfg.xml -  hibernate.connection.provider_class
     */
    public DBConnectionProvider() {
        instance = this;
    }

    public static DBConnectionProvider instance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        totalRequests.incrementAndGet();
        Connection conn = connections.get();
        if (conn == null) {
            conn = super.getConnection();

            log.info("Building new physical connection for user {}  by thread {}", conn.getMetaData().getUserName(), Thread.currentThread().getName());
            freshConnection.incrementAndGet();
            addShutDownHook(conn);
            connections.set(conn);
        } else {
            requestsServedFromCache.incrementAndGet();
        }
        log.info("Returning existing ThreadLocal connection");
        return connections.get();

    }

    @Override
    public void closeConnection(Connection conn) {
    }

    public static void logStat() {
        log.info("Total JDBC Connection requests: {} ; Total requests served from cache: {} ; Total new connections made : {} ",
                totalRequests.get(), requestsServedFromCache.get(), freshConnection.get());
    }

    private static void addShutDownHook(final Connection connection) {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
            {
                if (connection != null) {
                    try {
                        System.out.println("Closing Oracle DB connection ...");
                        connection.close();
                    } catch (SQLException ignore) {
                    }
                }
            }));
    }

}
