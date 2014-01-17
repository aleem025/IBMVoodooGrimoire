package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.FieldSet;

public class DocumentRecord extends BWCRecord {
	public DocumentRecord(FieldSet data) throws Exception {
		super(data);
		module = sugar.documents;
		recordData.putAll(data);
	}
} // DocumentRecord
