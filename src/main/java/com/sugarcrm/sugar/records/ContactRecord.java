package com.sugarcrm.sugar.records;

import com.sugarcrm.candybean.datasource.FieldSet;

public class ContactRecord extends StandardRecord {
	public String firstName, lastName, emailAddress;

	public ContactRecord(FieldSet data) throws Exception{
		super(data);
		module = sugar.contacts;

		firstName = data.get("firstName");
		lastName = data.get("lastName");
		emailAddress = data.get("emailAddress");
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
	
} // ContactRecord