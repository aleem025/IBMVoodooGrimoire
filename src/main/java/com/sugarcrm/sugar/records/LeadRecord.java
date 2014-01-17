package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.FieldSet;

public class LeadRecord extends StandardRecord {
	public String firstname, lastname;
	
	public LeadRecord(FieldSet data) throws Exception {
		super(data);
		module = sugar.leads;
		firstname = data.get("firstname");
		lastname = data.get("lastname");
	}
	
	/**
	 * getRecordIdentifier will return the Person Type Records First Name
	 * @return - String of the records Identification (first name)
	 */
	@Override
	public String getRecordIdentifier(){
		//TODO: This method needs to be made to handle different situations
		return recordData.get("firstName");
	}
	
} // end LeadRecord