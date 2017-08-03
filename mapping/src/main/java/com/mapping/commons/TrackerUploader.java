package com.mapping.commons;

import org.apache.poi.ss.usermodel.Row;

public interface TrackerUploader<T> {

	void initialize(String template);

	boolean isValidTemplate();

	T parse(Row row);

	void save(T record);

}
