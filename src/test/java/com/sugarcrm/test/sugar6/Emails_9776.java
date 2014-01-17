package com.sugarcrm.test.sugar6;

import org.junit.Ignore;
import org.junit.Test;
import com.sugarcrm.test.SugarTest;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;
import com.sugarcrm.sugar.records.ContactRecord;

public class Emails_9776 extends SugarTest {
	ContactRecord myContact;

	public void setup() throws Exception {
		sugar.login();
		
		// Create a related contact.
		myContact = (ContactRecord)sugar.contacts.api.create(); 

	}

	@Test
	@Ignore
	public void execute() throws Exception {
		FieldSet email = new FieldSet();
		email.put("subject", "Email subject");
		email.put("body", "This is the email body");
		
		DataSource emailOptions = new DataSource();
		emailOptions.add(email);
		
		myContact.saveDraft(emailOptions);
		// TODO: sugar.Emails.verify();
		// TODO: sugar.Emails.getContact();
		
		// Populate emailOptions with the subject, the To field is taken from the myContact object.
//		FieldSet emailOptions = new FieldSet();
//		emailOptions[0].add("subject", "Email Subject");
//							
//		myEmail = myContact.createDraftEmail(emailOptions); // Using the Contact Activities subpanel
//
//		// Populate emailData with the expected results including the Contact as the receiver  
//		FieldSet emailData = new FieldSet();
//		emailData[0].add("to", myContact.lastName);
//		emailData[0].add("subject", "Email Subject");
//		
//		myEmail.verifyData(emailData);

//		Login Sugar CRM as a valid user 
//		Go to Contacts module | one record view
//		Click Email button of Activities sub panel
//		  For action(3),
//	        compose view stay on Contacts module and all full function show
//	        "To" field is filled with all selected record's email address
//		Fill all required field
//		Click "Save draft"
//		Go Email module draft list view check saved draft.
//		  For action(6), saved draft be saved in draft list
	}

	public void cleanup() throws Exception {
		sugar.contacts.api.deleteAll();
		//sugar.emails.deleteAll();
		sugar.logout();
	}
}
