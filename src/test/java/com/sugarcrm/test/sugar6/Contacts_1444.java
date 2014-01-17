package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.ContactRecord;


public class Contacts_1444 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	@Ignore
	public void execute() throws Exception {
		// To be created with default data.
		
		ContactRecord myContact = (ContactRecord)sugar.contacts.create();  

		// Verify the returned data object using the GUI
		myContact.verify();
		
		FieldSet editedData = new FieldSet();
		editedData.put("firstName", "Mazen");
		editedData.put("lastName", "Louis");
		
		myContact.edit(editedData);
		
		myContact.verify(editedData);
	}

	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}
