package com.mapping.parser.app;

import org.apache.poi.ss.usermodel.Row;

import com.mapping.commons.TrackerUploader;
import com.mapping.parser.input.PostingTracker;

public class PostingTrackerUploader implements TrackerUploader<PostingTracker> {

	@Override
	public void initialize(String template) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidTemplate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PostingTracker parse(Row row) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(PostingTracker record) {
		// TODO Auto-generated method stub

	}
}
