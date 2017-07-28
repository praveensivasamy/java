package com.praveen.commons.hibernate;

import java.io.Serializable;
import java.sql.Types;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;

/**
 * 
 * The generic dao implementation class using JPA/Hiberntae
 * 
 * @author Praveen
 *
 */
public class JpaDao {

    private static final Logger log = LoggerFactory.getLogger(JpaDao.class);

    public Session session;
    public String defaultSchema;

    protected JpaDao(HibernateProvider provider) {
	session = provider.getSession();
	if (log.isDebugEnabled()) {
	    log.debug("New JPADao intance created. Session :{} ", session);
	}
    }

    public static JpaDao instance(HibernateProvider hibernateProvider) {
	return new JpaDao(hibernateProvider);
    }

    /**
     * Direct access to Hibernate {@link Session}
     * 
     * @return the session associated with this instance
     */
    public Session getSession() {
	return session;
    }

    public void startTransaction() {
	session.beginTransaction();
    }

    /**
     * @param entityClass
     *            the entity class
     * @param id
     *            the entity identifier
     * @return the entity with the specifed identifier or <code>null</code> if
     *         no such entity exists
     */
    public <T> T find(Class<T> entityClass, Serializable id) {
	return session.get(entityClass, id);
    }

    /**
     * @param id
     *            JPA query string
     * @return the {@link List} of entities
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> query(String queryString) {
	return session.createQuery(queryString).list();
    }

    /**
     * @param queryString
     *            the jpa query string
     * @param firstResult
     *            the count of firstResult, -1 if not relevant
     * @param maxResults
     *            the max size of the results, -1 if not relevant
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> query(String queryString, int firstResult, int maxResults) {
	Query query = session.createQuery(queryString);
	if (firstResult >= 0) {
	    query.setFirstResult(firstResult);
	}
	if (maxResults > 0) {
	    query.setMaxResults(maxResults);
	}
	return query.list();
    }

    /**
     * @param entityClass
     *            the entity class
     * @return all entities of the specified type
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> readAll(Class<T> entityClass) {
	return session.createQuery("from " + entityClass.getSimpleName()).list();
    }

    /**
     * @param entityClass
     *            the entity class
     * @param queryParameters
     *            the array of {@link QueryParameter}
     * @return the list of entities complying with the query parameters
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryByCriteria(Class<T> entityClass, QueryParameter... queryParameters) {
	Criteria criteria = session.createCriteria(entityClass);
	for (QueryParameter queryParameter : queryParameters) {
	    criteria.add(Restrictions.eq(queryParameter.getName(), queryParameter.getValue()));
	}
	return criteria.list();
    }

    /**
     * Example: queryByCriteria(Trade.class, posCode, 12345, refNoLocal, 'abcd')
     * 
     * @param entityClass
     *            the entity class
     * @param restrictions
     *            the varargs of attributeName/attributeValue restrictions
     * @return the list of entities complying with the restrictions
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryByCriteria(Class<T> entityClass, Object... restrictions) {
	Criteria criteria = session.createCriteria(entityClass);
	for (int i = 0; i < restrictions.length; i++) {
	    criteria.add(Restrictions.eq((String) restrictions[i], restrictions[i + 1]));
	    i++;
	}
	return criteria.list();
    }

    /**
     * @param entityClass
     *            the entity class
     * @param queryParameters
     *            the array of {@link QueryParameter}
     * @return the single entity complying with the query parameters or
     *         <code>null</code> in none exists
     * @throws FTPException
     *             if more than one entity is found
     */
    @SuppressWarnings("unchecked")
    public <T> T findByCriteria(Class<T> entityClass, QueryParameter... queryParameters) {
	List<?> l = queryByCriteria(entityClass, queryParameters);
	if (l.isEmpty()) {
	    return null;
	}
	if (l.size() > 1) {
	    throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION)
		    .details("More than one entity instance of type " + entityClass.getSimpleName() + "found ");
	}
	return ((T) l.get(0));
    }

    /**
     * @param entityClass
     *            the entity class
     * @param restrictions
     *            the varargs of attributeName/attributeValue restrictions
     * @return the single entity complying with the restrictions or
     *         <code>null</code> if none exists
     * @throws FTPException
     *             if more than one entity is found
     */
    @SuppressWarnings("unchecked")
    public <T> T findByCriteria(Class<T> entityClass, Object... restrictions) {
	List<?> l = queryByCriteria(entityClass, restrictions);
	if (l.isEmpty()) {
	    return null;
	}
	if (l.size() > 1) {
	    throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION)
		    .details("More than one entity instance of type " + entityClass.getSimpleName() + "found ");
	}
	return ((T) l.get(0));
    }

    /**
     * Retrieve the named Hibernate query, defined in the orm.xml file
     * 
     * @param queryName
     *            the name of the query
     * @return the resulting {@link Query} object
     */
    public Query getNamedQuery(String queryName) {
	return session.getNamedQuery(queryName);
    }

    /**
     * Persist the given entity
     * 
     * @param t
     *            a detached entity instance
     * @return the persisted entity (some object instance as the input
     *         parameter)
     */
    public <T> void save(T t) {
	session.save(t);
    }

    public <T> void saveOrUpdate(T t) {
	session.saveOrUpdate(t);
    }

    public <T> void delete(T t) {
	session.delete(t);
    }

    public <T> void delete(List<T> l) {
	for (T t : l) {
	    session.delete(t);
	}
    }

    /**
     * Persist the entities in the given {@link List}
     * 
     * @param l
     */
    public <T> void save(List<T> l) {
	for (T t : l) {
	    session.save(t);
	}
    }

    /**
     * Flush the current session
     */
    public void flush() {
	session.flush();
    }

    /**
     * Clear the current Hibernate {@link Session}
     */
    public void clear() {
	log.trace("Clear the current session");
	session.clear();
    }

    /**
     * Flush and clear the current session
     * <p>
     * To be used to manage bulk inserts, see
     * {@link http://docs.jboss.org/hibernate/orm/3.3/reference/en/html/batch.html#batch-inserts}
     */
    public void flushAndClear() {
	log.trace("Flush and log the current session");
	session.flush();
	session.clear();
    }

    /**
     * Start a transaction
     */
    public void beginTransaction() {
	session.beginTransaction();
    }

    /**
     * Commit the current transaction
     */
    public void commit() {
	session.getTransaction().commit();
    }

    /**
     * Convenience method to get a {@link ScrollableResults} with the specified
     * parameters
     * 
     * @param session
     * @param queryString
     *            the JPA query string
     * @param fetchSize
     *            the ResultSet fetch size
     * @param queryHint
     *            the Oracle query hint
     * @param params
     *            the array of {@link QueryParameter}s
     * @return
     */
    public ScrollableResults getResultSet(String queryString, int fetchSize, String queryHint,
	    QueryParameter... params) {
	Query query = session.createQuery(queryString);
	query.setFetchSize(fetchSize);
	if (queryHint != null) {
	    query.addQueryHint(queryHint);
	}
	for (QueryParameter param : params) {
	    query.setParameter(param.getName(), param.getValue());
	}
	return query.scroll(ScrollMode.FORWARD_ONLY);
    }

    /**
     * {@link #getResultSet(String, int, int, QueryParameter...)} with
     * pagination support
     */
    public ScrollableResults getResultSet(String queryString, int firstResult, int maxResults,
	    QueryParameter... params) {
	Query query = session.createQuery(queryString);
	query.setFetchSize(1000);
	for (QueryParameter param : params) {
	    query.setParameter(param.getName(), param.getValue());
	}
	query.setFirstResult(firstResult);
	query.setMaxResults(maxResults);
	return query.scroll(ScrollMode.FORWARD_ONLY);
    }

    public String getFunctionNameOutOfCall(String functionCall) {
	StringTokenizer st = new StringTokenizer(functionCall, ".");
	String functionName = "";
	while (st.hasMoreTokens()) {
	    functionName = st.nextToken();
	}
	return functionName;
    }

    public List<?> runNativeQuery(String query) {
	SQLQuery sqlQuery = session.createSQLQuery(query);
	return sqlQuery.list();
    }

    /**
     * {@link #runNativeQuery(String, int, int, QueryParameter...)} with
     * pagination support
     */
    public ScrollableResults runNativeQuery(String queryString, int fetchSize, String queryHint,
	    QueryParameter... params) {
	SQLQuery sqlQuery = session.createSQLQuery(queryString);
	sqlQuery.setFetchSize(fetchSize);
	if (queryHint != null) {
	    sqlQuery.addQueryHint(queryHint);
	}
	for (QueryParameter param : params) {
	    sqlQuery.setParameter(param.getName(), param.getValue());
	}
	return sqlQuery.scroll(ScrollMode.FORWARD_ONLY);
    }

    /**
     * Convenience method to get {@link ScrollableResults} for oracle native SQL
     * and transform the results with {@link Transformers}
     * 
     * @param queryString
     *            - the JPA query string
     * @param fetchSize
     *            - the ResultSet fetch size
     * @param queryHint
     *            - Oracle HINTs
     * @param transformer
     *            - A bean to store results
     * @param columnAliasMapping
     *            - {@link ColumnAliasMapping}
     * @param params
     *            - {@link QueryParameter}
     * @return
     */
    public ScrollableResults runTransFormableResultSQLQuery(String queryString, int fetchSize, String queryHint,
	    Class<?> transformer, ColumnAliasMapping[] columnAliasMapping, QueryParameter... params) {

	SQLQuery sqlQuery = session.createSQLQuery(queryString);
	sqlQuery.setFetchSize(fetchSize);
	if (queryHint != null) {
	    sqlQuery.addQueryHint(queryHint);
	}
	for (ColumnAliasMapping columnResultParam : columnAliasMapping) {
	    Type columnType = columnResultParam.getColumnType();
	    if (columnType == null) {
		sqlQuery.addScalar(columnResultParam.getAliasName());
	    } else {
		sqlQuery.addScalar(columnResultParam.getAliasName(), columnResultParam.getColumnType());
	    }
	}
	for (QueryParameter param : params) {
	    sqlQuery.setParameter(param.getName(), param.getValue());
	}
	if (transformer != null) {
	    sqlQuery.setResultTransformer(Transformers.aliasToBean(transformer));
	}
	return sqlQuery.scroll();
    }

    public int runNativeInsert(String query) {
	SQLQuery sqlQuery = session.createSQLQuery(query);
	return sqlQuery.executeUpdate();
    }

    public int runNativeUpdate(String query) {
	SQLQuery sqlQuery = session.createSQLQuery(query);
	return sqlQuery.executeUpdate();
    }

    public int runJpaUpdate(String jpaUpdate, QueryParameter... parameters) {
	Query query = session.createQuery(jpaUpdate);
	for (QueryParameter param : parameters) {
	    query.setParameter(param.getName(), param.getValue());
	}
	return query.executeUpdate();
    }

    public void runNativeSql(String sql) {
	SQLQuery sqlQuery = session.createSQLQuery(sql);
	sqlQuery.executeUpdate();
    }

    /**
     * Invoke the specified stored procedure in the given {@link Session}
     * 
     * @param procedureName
     *            the procedure in arguments
     * @param arguments
     */
    public void invokeSqlProcedure(final String procedureName, Object... arguments) {
	log.debug("Invoking native SQL procedure: {} with arguments {}", procedureName, arguments);
	NativeWork work = new NativeWork(procedureName, arguments);
	session.doWork(work);
    }

    /**
     * Invoke the specified stored function in the given {@link Session}
     * <p>
     * 
     * @param functionName
     *            the name of the stored function
     * @param returnType
     *            the returnType, ie one of the constants defined in the
     *            {@link Types} class
     * @param arguments
     *            the function in arguments
     */
    public Object invokeSqlFunction(final String functionName, int returnType, Object... arguments) {
	log.debug("Invoking native SQL function: {}", functionName);
	NativeWork work = new NativeWork(functionName, returnType, arguments);
	session.doWork(work);
	return work.getResult();
    }

    @Override
    public String toString() {
	return "JPADao[hibSession:" + System.identityHashCode(session) + "][defaultSchema:" + defaultSchema + "]";
    }

    public String getDefaultSchema() {
	return defaultSchema;
    }

}
