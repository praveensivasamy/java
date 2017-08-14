package com.mapping.commons;

import org.apache.poi.ss.usermodel.Row;

import com.mapping.parser.app.AbstractTemplateUploader;
import com.mapping.parser.input.AbstractMappingEntity;

/**
 * {@link AbstractTemplateUploader}'s key steps
 *
 * @author Praveen Sivasamy
 *
 * @param <T> {@link AbstractMappingEntity}
 */
public interface ITemplateUploader<T extends AbstractMappingEntity> {

	void initialise();

	boolean isValidTemplate();

	T parse(Row row);

	void save(T record);

}
