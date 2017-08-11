package com.mapping.commons;

import com.mapping.parser.app.AbstractTemplateUploader;

/**
 * Global constants for {@link AbstractTemplateUploader}
 *
 * @author Praveen Sivasamy
 *
 */
public class MappingConstants {

	protected static String MAPPING_CONFIG_FILE = "mapping.hibernate.cfg.xml";

	protected static int HEADER_ROW = 0x00; //First row in the template
	protected static String RELEVANT_SHEET = "IS-BFS EUC 1.1-Group1";

	protected static int TRIMATRIX_CHECK_FILTER_CELL = 0x05; //Fifth column in the template
	protected static String TRIMATRIX_CLIENT_FILTER = "COMMERZBANK AG";

	protected static int COLLECTION_CHECK_FILTER_CELL = 0x03; //Third column in the template
	protected static String COLLECTION_CLIENT_FILTER = "COMMERZ"; //to support both Commerzbank and CommerzFinanz

}
