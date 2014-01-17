package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.FieldSet;

public class OpportunityRecord extends StandardRecord {
	// TODO: Complete the list of fields.  A data structure may be better.
	public String name, assignedTo, accountName;
	
	public OpportunityRecord(FieldSet data) throws Exception {
		super(data);
		module = sugar.opportunities;
		recordData.putAll(data);
	}
} // OpportunityRecord
