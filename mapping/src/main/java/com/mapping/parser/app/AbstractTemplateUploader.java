package com.mapping.parser.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapping.commons.ITemplateUploader;
import com.mapping.commons.MappingConstants;
import com.mapping.parser.input.AbstractMappingEntity;
import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;
import com.praveen.commons.hibernate.HibernateProvider;
import com.praveen.commons.hibernate.JpaDao;

/**
 * Parse a given excelsheet and persist in a database
 *
 * @author Praveen Sivasamy
 *
 * @param <T>
 *            Entity for persist
 */
public abstract class AbstractTemplateUploader<T extends AbstractMappingEntity> extends MappingConstants implements ITemplateUploader<T> {

	private static final Logger log = LoggerFactory.getLogger(AbstractTemplateUploader.class);

	private static HibernateProvider configProvider = null;
	private static JpaDao dao = null;

	protected static Workbook workbook = null;
	protected static Sheet sheet = null;
	/** Input file to persist in DB */
	protected File templateFile;
	/** Retrieve the persistent class from the type T */
	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public AbstractTemplateUploader(File templateFile) throws ApplicationException {
		this.templateFile = templateFile;
		if (!templateFile.exists()) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION).details("The input file : " + templateFile + " not found or does not exist!");
		}
		this.persistentClass = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	/**
	 * Load the template {@link AbstractTemplateUploader#templateFile} in memory
	 */
	@Override
	public void initialise() {
		try {
			if (workbook == null) {
				workbook = WorkbookFactory.create(templateFile);
			}
			sheet = workbook.getSheet(RELEVANT_SHEET);
		} catch (InvalidFormatException e) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e).details("Invalid input file" + templateFile);
		} catch (IOException e) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e).details("Error processing the file  : " + templateFile);
		}
	}

	/**
	 * initialise the {@link JpaDao}
	 */
	private void initialiseHibernate() {
		configProvider = HibernateProvider.instance(MAPPING_CONFIG_FILE, null);
		dao = JpaDao.instance(configProvider);
		log.info(dao.toString());
	}

	/**
	 * Implementation to check if the given {@link AbstractTemplateUploader#templateFile} is
	 * valid
	 */
	@Override
	public abstract boolean isValidTemplate();

	/**
	 * Parse the {@link Row} of {@link AbstractTemplateUploader#templateFile}
	 */
	@Override
	public abstract T parse(Row row);

	/**
	 * process the each {@link Row} and persist to database
	 */
	protected void process() {
		if (isValidTemplate()) {
			try {
				dao.beginTransaction();
				for (Row row : sheet) {
					T destinationRecord = parse(row);
					if (destinationRecord.isSave()) {
						destinationRecord.setUploadedFile(templateFile.getName());
						save(destinationRecord);
					}
				}
				dao.flushAndClear();
				dao.commit();
			} catch (Exception ex) {
				dao.rollBack();
				throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, ex).details("Error during save");
			} finally {
				tearDown();
			}
		} else {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION).details("Invalid Template for parsing" + templateFile);
		}
	}

	@Override
	public void save(T destinationRecord) {
		dao.saveOrUpdate(destinationRecord);
	}

	/**
	 * check if the {@link AbstractTemplateUploader#templateFile} is already uploaded to DB
	 *
	 * @return
	 */
	private boolean isTemplateProcessed() {
		initialiseHibernate();
		log.info("Checking {}.class", persistentClass.getSimpleName());
		Long count = dao.getCountByCriteria(persistentClass, "uploadedFile", templateFile.getName());
		return count > 0;
	}

	protected void run() {

		if (isTemplateProcessed()) {
			log.error("Already processed : {}", templateFile);
			tearDown();
			return;
		}

		initialise();
		process();
		tearDown();
	}

	private void tearDown() {
		HibernateProvider.tearDownAll();
	}

}
