package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.FieldSet;

public class AccountRecord extends StandardRecord {
	// TODO: Complete the list of fields.  A data structure may be better.
	public String name, assignedTo;
	
	public AccountRecord(FieldSet data) throws Exception {
		super(data);
		module = sugar.accounts;
		recordData.putAll(data);
	}
} // AccountRecord
