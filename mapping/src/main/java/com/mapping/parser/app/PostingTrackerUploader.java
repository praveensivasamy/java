package com.mapping.parser.app;

import java.io.File;

import org.apache.poi.ss.usermodel.Row;

import com.mapping.parser.input.PostingTracker;
import com.praveen.commons.exception.ApplicationException;

public class PostingTrackerUploader extends AbstractTemplateUploader<PostingTracker> {

	public PostingTrackerUploader(File templateFile) throws ApplicationException {
		super(templateFile);
	}

	@Override
	public boolean isValidTemplate() {
		return false;
	}

	@Override
	public PostingTracker parse(Row row) {
		return null;
	}

}
