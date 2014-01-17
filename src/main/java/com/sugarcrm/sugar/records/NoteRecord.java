package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.FieldSet;

public class NoteRecord extends StandardRecord {
	
	public NoteRecord(FieldSet data) throws Exception {
		super(data);
		module = sugar.notes;
		recordData.putAll(data);
	}
} // end NoteRecord