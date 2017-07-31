package com.mapping.parser;

import org.apache.poi.ss.usermodel.Row;

public interface TrackerParser<T> {

	void initialize(String template);

	boolean isValidTemplate();

	T parse(Row row);

	void save(T record);

}
