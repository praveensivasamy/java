package com.mapping.parser.app;

import java.io.File;

import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapping.commons.Validator;
import com.mapping.parser.input.PostingTracker;
import com.praveen.commons.exception.ApplicationException;

public class PostingTrackerUploader extends AbstractTemplateUploader<PostingTracker> {

	private static final Logger log = LoggerFactory.getLogger(PostingTrackerUploader.class);

	public PostingTrackerUploader(File templateFile) throws ApplicationException {
		super(templateFile);
	}

	@Override
	public boolean isValidTemplate() {
		return Validator.validatePostingTracker(sheet.getRow(HEADER_ROW));
	}

	@Override
	public PostingTracker parse(Row row) {
		return null;
	}

	public static void main(String[] args) {
		String inFile = "";
		File inputTemplate = new File(inFile);
		new PostingTrackerUploader(inputTemplate).run();
	}

}
