package com.praveen.commons.hibernate;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.commons.utils.ToStringUtils;

/**
 * Hibernate {@link Session} and {@link Connection} provider
 * <p>
 * The instances are cached per hibernate configuration file
 * 
 * 
 * @author Praveen Sivasamy
 */
public class HibernateProvider {

    private static final Logger log = LoggerFactory.getLogger(HibernateProvider.class);

    private static Map<String, HibernateProvider> instances = new HashMap<String, HibernateProvider>();

    private SessionFactory sessionFactory;
    private String configFile;
    private String defaultSchema;
    private String url;
    private String user;
    private String password;

    /** The {@link Interceptor} for this instance, if any */
    private Interceptor interceptor;

    private HibernateProvider(String configFile, Interceptor dbInterceptor) {
	this.configFile = configFile;
	this.interceptor = dbInterceptor;
	initializeSessionFactory();
    }

    private void initializeSessionFactory() {
	long time = System.currentTimeMillis();
	try {
	    StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(configFile).build();
	    Metadata metaData = new MetadataSources(ssr).getMetadataBuilder().build();
	    sessionFactory = metaData.getSessionFactoryBuilder().build();

	} catch (Exception e) {
	    log.error("Error in Hibernate initialization: " + this.url + ";" + this.user, e);
	    // hibernate background thread prevents clean application exit - cannot call sessionFactory.close()
	    // as the sessionFactory will be null in case of exceptions
	    System.exit(1);
	}
	log.info("Hibernate initialized in " + (System.currentTimeMillis() - time) + " ms.");
    }

    /**
     * @param configFile the hibernate configuration file
     * @param dbInterceptor a {@link Interceptor} instance, optional. <code>null</code> if no interceptor is required
     * @return the {@link HibernateProvider} instance for the above parameters
     */
    public static HibernateProvider instance(String configFile, Interceptor dbInterceptor) {
	log.info("configFile : {} , interceptor : {}", configFile, dbInterceptor);
	synchronized (HibernateProvider.class) {
	    HibernateProvider res = instances.get(getMapKey(configFile, dbInterceptor));
	    if (res == null) {
		res = new HibernateProvider(configFile, dbInterceptor);
		instances.put(getMapKey(configFile, dbInterceptor), res);
	    }

	    return res;
	}
    }

    /**
     * Get a new {@link HibernateProvider} instance from the given hibernate cfg file and a connection string in format:
     * url#user#password#schema
     * <p>
     * Required if the db conn parameters are set as system props from command line
     */
    public static HibernateProvider instance(String configFile, String connString, Interceptor dbInterceptor) {
	log.info("configFile: {} , connectionString : {} , interceptor : {}", configFile, connString, dbInterceptor);
	synchronized (HibernateProvider.class) {
	    HibernateProvider res = instances.get(getMapKey(configFile, connString, dbInterceptor));
	    if (res == null) {
		res = new HibernateProvider(configFile, connString, dbInterceptor);
		instances.put(getMapKey(configFile, connString, dbInterceptor), res);
	    }
	    return res;
	}
    }

    private HibernateProvider(String configFile, String connString, Interceptor dbInterceptor) {
	this.configFile = configFile;
	this.interceptor = dbInterceptor;
	url = ToStringUtils.getUrl(connString);
	user = ToStringUtils.getUser(connString);
	password = ToStringUtils.getPassword(connString);
	defaultSchema = ToStringUtils.getSchema(connString);

	initializeSessionFactory();
    }

    private static String getMapKey(String hibernateConfigFile, Interceptor dbInterceptor) {
	return hibernateConfigFile + ":" + (dbInterceptor == null ? "null" : dbInterceptor.getClass().getSimpleName());
    }

    private static String getMapKey(String hibernateConfigFile, String dbConn, Interceptor interceptor) {
	return hibernateConfigFile + ":" + dbConn + ":" + (interceptor == null ? "null" : interceptor.getClass().getSimpleName());
    }

    private SessionFactory getSessionFactory() {
	return sessionFactory;
    }

    /**
     * @return a new Hibernate {@link Session}
     */
    public Session getSession() {
	return getSessionFactory().openSession();
    }

    public Interceptor getInterceptor() {
	return interceptor;
    }

    /**
     * @return a new Hibernate {@link StatelessSession}
     */
    public StatelessSession getStatlessSession() {
	return getSessionFactory().openStatelessSession();
    }

    /**
     * Close Hibernate {@link SessionFactory}
     * <p>
     * Make sure this method is called always (use a finally block) - otherwise the application will not terminate
     */
    public void tearDown() {
	log.info("Closing SessionFactory");
	getSessionFactory().close();
	for (String key : instances.keySet()) {
	    if (instances.get(key) == this) {
		instances.remove(key);
		break;
	    }
	}
    }

    /**
     * Close all available {@link HibernateProvider} instances
     */
    public static final void tearDownAll() {
	for (HibernateProvider instance : instances.values()) {
	    log.info("Closing SessionFactory");
	    instance.getSessionFactory().close();
	}
	instances.clear();
    }
}
