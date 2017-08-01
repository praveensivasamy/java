package com.mapping.parser.app;

import org.apache.poi.ss.usermodel.Row;

public interface TrackerUploader<T> {

	void initialize(String template);

	boolean isValidTemplate();

	T parse(Row row);

	void save(T record);

}
