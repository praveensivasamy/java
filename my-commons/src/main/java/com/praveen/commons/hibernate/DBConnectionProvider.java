package com.praveen.commons.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

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

	private static volatile int totalRequests = 0;
	private static volatile int requestsServedFromCache = 0;
	private static final Logger log = LoggerFactory.getLogger(DBConnectionProvider.class);
	private static final long serialVersionUID = 1L;

	private ThreadLocal<Connection> connections = new ThreadLocal<Connection>();
	private static DBConnectionProvider instance;

	public DBConnectionProvider() {
		instance = this;
	}

	@Override
	public Connection getConnection() throws SQLException {
		totalRequests++;
		if (connections.get() == null) {
			Connection conn = super.getConnection();
			log.info("Setting new " + conn + " to the ThreadLocal pool");
			addShutDownHook(conn);
			connections.set(conn);
		} else {
			requestsServedFromCache++;
		}
		log.info("Returning existing ThreadLocal connection");
		return connections.get();
	}

	public static DBConnectionProvider instance() {
		return instance;
	}

	@Override
	public void closeConnection(Connection conn) throws SQLException {
		super.closeConnection(conn);
	}

	public static void logStat() {
		log.info("Total JDBC Connection requests: " + totalRequests + "; Total requests served from cache: " + requestsServedFromCache);
	}

	private static void addShutDownHook(final Connection connection) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				if (connection != null) {
					try {
						System.out.println("Closing Oracle DB connection ...");
						connection.close();
					} catch (SQLException ignore) {
					}
				}
			}
		}));
	}

}
