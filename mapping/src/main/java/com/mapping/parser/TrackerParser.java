package com.mapping.parser;

import org.apache.poi.ss.usermodel.Row;

public interface TrackerParser<T> {

	boolean isValidateTemplate(String template);

	T parse(Row row);

	void save(T record);

}
